package com.bitwormhole.passport.data.converters;


import com.bitwormhole.passport.utils.ResultSet;


// converter for <DTO, BO, Entity>
public interface IConverter<D, B, E> {

    ResultSet<E> bo2entity(B... src);

    ResultSet<B> entity2bo(E... src);

    ResultSet<B> dto2bo(D... src);

    ResultSet<D> bo2dto(B... src);

}
