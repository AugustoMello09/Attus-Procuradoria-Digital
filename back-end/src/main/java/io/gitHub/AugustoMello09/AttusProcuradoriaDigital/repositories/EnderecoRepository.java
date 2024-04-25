package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, UUID>{

}
