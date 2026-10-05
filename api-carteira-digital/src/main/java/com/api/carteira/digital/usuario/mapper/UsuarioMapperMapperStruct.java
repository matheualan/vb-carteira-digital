package com.api.carteira.digital.usuario.mapper;

import com.api.carteira.digital.usuario.dto.UsuarioRequest;
import com.api.carteira.digital.usuario.dto.UsuarioResponse;
import com.api.carteira.digital.usuario.model.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

/**
 Usando MapStruct:
 1) Adiciona a dependência no pom.xml e o annotation processor
 2) Cria essa interface com os métodos de mapeamento
 3) Injeta dependência na service ou classe que for usar o mapper e basta chamar os métodos

 - O MapStruct vai gerar a implementação dessa interface em tempo de compilação
 - A implementação gerada vai ser usada pelo Spring para injetar o mapper onde for necessário
 - O MapStruct é mais performático do que o ModelMapper, pois ele gera código de mapeamento em tempo de compilação, enquanto o ModelMapper faz isso em tempo de execução
 */

@Mapper(componentModel = "spring")
public interface UsuarioMapperMapperStruct {

    Usuario toEntity(UsuarioRequest dto);
    Usuario toEntity(UsuarioResponse dto);

    UsuarioResponse toResponse(Usuario usuario);

    List<UsuarioResponse> toListDTO(List<Usuario> usuarios);
}