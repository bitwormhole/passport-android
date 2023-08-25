package com.bitwormhole.passport.supports.keys;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import com.bitwormhole.passport.contexts.ISession;
import com.bitwormhole.passport.services.KeyPairService;
import com.bitwormhole.passport.utils.BinaryUtils;
import com.bitwormhole.passport.utils.HashUtils;
import com.bitwormhole.passport.web.dto.KeyPairDTO;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertPathBuilder;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.x500.X500Principal;

public class DefaultKeyManager implements KeyPairService {

    private KeyStore cachedKS;

    @Override
    public KeyPair getAppKeyPair(boolean create) {
        return this.openKeyPair("passport-app", create);
    }

    @Override
    public KeyPair getUserKeyPair(ISession session, boolean create) {
        String alias = this.makeAliasForUser(session);
        return this.openKeyPair(alias, create);
    }

    private String makeAliasForUser(ISession session) {
        final char nl = '\n';
        String domain = session.getUserDomain() + "";
        String user = session.getUserName() + "";
        StringBuilder builder = new StringBuilder();
        builder.append(domain.trim()).append(nl);
        builder.append(user.trim()).append(nl);
        byte[] plain = builder.toString().getBytes(StandardCharsets.UTF_8);
        byte[] sum = HashUtils.computeSHA1sum(plain);
        String hex = BinaryUtils.stringify(sum);
        return "pp-user-" + hex.substring(0, 8);
    }

    private KeyPair openKeyPair(String alias, boolean create) {
        KeyStore ks = this.getKeyStore();
        char[] pass = "todo:demo".toCharArray();
        try {
            Enumeration<String> all = ks.aliases();
            List<String> list = new ArrayList<>();
            for (; all.hasMoreElements(); ) {
                String a = all.nextElement();
                list.add(a);
            }

            KeyPair kp = this.loadKeyPair(ks, alias, pass);
            if (kp != null) {
                return kp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!create) {
            return null;
        }
        try {
            KeyPair kp = this.createNewKeyPair(ks, alias, pass);
            if (kp != null) {
                return kp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private KeyPair loadKeyPair(KeyStore ks, String alias, char[] pass) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        Certificate cert = ks.getCertificate(alias);
        PrivateKey key = (PrivateKey) ks.getKey(alias, pass);
        if (cert == null || key == null) {
            return null;
        }
        PublicKey pub = cert.getPublicKey();
        return new KeyPair(pub, key);
    }

    private KeyPair createNewKeyPair(KeyStore ks, String alias, char[] pass) throws KeyStoreException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CertificateException, IOException {
        final int purposes = KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY | KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT;
        final long day = 1000 * 3600 * 24;
        Date t1 = new Date();
        Date t2 = new Date(t1.getTime() + (30 * 365 * day));
        BigInteger sn = BigInteger.valueOf(t1.getTime());

        StringBuilder name = new StringBuilder();
        Map<String, String> kv = new HashMap<String, String>();
        kv.put("CN", alias + "");
        kv.put("L", "gl");
        kv.put("C", "gx");
        kv.forEach((k, v) -> {
            int len = name.length();
            String sep = (len > 0) ? "," : "";
            name.append(sep).append(k);
            name.append("=").append(v);
        });

        KeyGenParameterSpec.Builder specBuilder = new KeyGenParameterSpec.Builder(alias, purposes)
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setCertificateNotBefore(t1)
                .setCertificateNotAfter(t2)
                .setCertificateSerialNumber(sn)
                .setCertificateSubject(new X500Principal(name.toString()));

        KeyGenParameterSpec spec = specBuilder.build();
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
        kpg.initialize(spec);
        return kpg.generateKeyPair();
    }

    @Override
    public KeyStore getKeyStore() {
        KeyStore ks = this.cachedKS;
        if (ks == null) {
            ks = this.loadKeyStore();
            this.cachedKS = ks;
        }
        return ks;
    }

    @Override
    public KeyPairDTO[] listKeyPairs() {
        List<KeyPairDTO> dst = new ArrayList<>();
        try {
            KeyStore ks = this.getKeyStore();
            Enumeration<String> aliases = ks.aliases();
            for (; aliases.hasMoreElements(); ) {
                String alias = aliases.nextElement();
                Certificate cert = ks.getCertificate(alias);
                KeyPairDTO o = new KeyPairDTO();
                loadCertInfo(cert, o);
                o.setAlias(alias);
                dst.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dst.toArray(new KeyPairDTO[dst.size()]);
    }

    private void loadCertInfo(Certificate cert, KeyPairDTO dst) {

        X509Certificate x509c = (X509Certificate) cert;
        X500Principal x500p = x509c.getSubjectX500Principal();

        dst.setDomain("-");
        dst.setUsername(x500p.getName());
        dst.setPublicKeyFingerprint("-");
        dst.setX500name(x500p.toString());
    }

    private KeyStore loadKeyStore() {
        try {
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            return ks;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
