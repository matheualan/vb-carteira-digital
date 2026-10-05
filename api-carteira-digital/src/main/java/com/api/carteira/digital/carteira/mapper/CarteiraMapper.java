package com.api.carteira.digital.carteira.mapper;

import com.api.carteira.digital.carteira.dto.CarteiraRequest;
import com.api.carteira.digital.carteira.dto.CarteiraResponse;
import com.api.carteira.digital.carteira.model.Carteira;
import org.springframework.stereotype.Component;

@Component
public class CarteiraMapper {

    public Carteira toEntity(CarteiraRequest dtoRequest) {
        return new Carteira(
                dtoRequest.nome(),
                dtoRequest.descricao(),
                dtoRequest.saldo()
        );
    }

    public CarteiraResponse toResponse(Carteira entity) {
        return new CarteiraResponse(
                entity.getNome(),
                entity.getDescricao(),
                entity.getSaldo()
        );
    }

}