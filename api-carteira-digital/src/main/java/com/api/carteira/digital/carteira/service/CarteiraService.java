package com.api.carteira.digital.carteira.service;

import com.api.carteira.digital.carteira.dto.CarteiraRequest;
import com.api.carteira.digital.carteira.dto.CarteiraResponse;
import com.api.carteira.digital.carteira.mapper.CarteiraMapper;
import com.api.carteira.digital.carteira.model.Carteira;
import com.api.carteira.digital.carteira.repository.CarteiraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarteiraService {

    private final CarteiraRepository carteiraRepository;
    private final CarteiraMapper carteiraMapper;

    public CarteiraResponse criarCarteira(CarteiraRequest dtoRequest) {
        Carteira entity = carteiraMapper.toEntity(dtoRequest);
        return carteiraMapper.toResponse(carteiraRepository.save(entity));
    }

    public List<CarteiraResponse> listarCarteiras() {
        return carteiraRepository.findAll().stream()
                .map(carteiraMapper::toResponse)
                .collect(Collectors.toList());
    }

}