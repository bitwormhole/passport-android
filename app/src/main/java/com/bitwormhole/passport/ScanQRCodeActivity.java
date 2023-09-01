package com.bitwormhole.passport;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageInfo;
import androidx.camera.core.ImageProxy;
import androidx.camera.view.LifecycleCameraController;
import androidx.camera.view.PreviewView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ScanQRCodeActivity extends Activity implements LifecycleOwner {

    private LifecycleRegistry lifecycleRegistry;
    private ExecutorService executorService;
    private Handler handler;
    private TextView mTextScanningResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_scan_qrcode);

        this.handler = new Handler();
        this.mTextScanningResult = findViewById(R.id.text_scanning_result);
        PreviewView previewView = findViewById(R.id.preview1);


        LifecycleCameraController cameraController = new LifecycleCameraController(this);
        cameraController.bindToLifecycle(this);
        cameraController.setCameraSelector(CameraSelector.DEFAULT_BACK_CAMERA);
        previewView.setController(cameraController);

        Executor executor = this.getAnalysisExecutor();
        cameraController.setImageAnalysisAnalyzer(executor, new MyAnalyzer());

        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

    public void onDestroy() {
        super.onDestroy();
        ExecutorService ser = this.executorService;
        this.executorService = null;
        if (ser != null) {
            ser.shutdown();
        }
    }

    private Executor getAnalysisExecutor() {
        ExecutorService ser = this.executorService;
        if (ser == null) {
            ser = Executors.newSingleThreadExecutor();
            this.executorService = ser;
        }
        return ser;
    }


    @Override
    protected void onStart() {
        super.onStart();
        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //    lifecycleRegistry.markState(Lifecycle.State.RESUMED);
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
    }


    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private class MyResultPostman implements Runnable {
        private final Result result;

        public MyResultPostman(Result res) {
            this.result = res;
        }

        @Override
        public void run() {
            if (acceptScanResult(result)) {
                handleScanResult(result);
            } else {
                rejectScanResult(result);
            }
        }
    }

    private void handleScanResult(Result res) {
        String text = res.getText();
        Log.i("scan-qrcode", "result.text = " + text);
        this.mTextScanningResult.setText(text);
    }

    private boolean acceptScanResult(Result res) {
        String text = res.getText() + "";
        return text.startsWith("https://");
    }

    private void rejectScanResult(Result res) {
        Vibrator vi = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        VibrationEffect effect = VibrationEffect.createOneShot(50, 80);
        vi.vibrate(effect);
    }


    private class MyAnalyzer implements ImageAnalysis.Analyzer {

        public void analyze(@NonNull ImageProxy imageProxy) {
            try {
                Bitmap bmp = imageProxy.toBitmap();
                Result result = decode(bmp);
                if (result == null) {
                    return;
                }
                handler.post(new MyResultPostman(result));
            } catch (Exception e) {
                // nop
            } finally {
                imageProxy.close();
            }
        }
    }


    private Result decode(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        final int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        RGBLuminanceSource luminanceSource = new RGBLuminanceSource(width, height, pixels);
        BinaryBitmap bb = new BinaryBitmap(new HybridBinarizer(luminanceSource));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bb);
        } catch (Exception e) {
            return null;
        }
    }

}
