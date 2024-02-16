package com.apiCaixaFinanceiro.apicaixa.services;

import com.apiCaixaFinanceiro.apicaixa.entities.MoedasEntity;
import com.apiCaixaFinanceiro.apicaixa.exceptions.BadRequestException;
import com.apiCaixaFinanceiro.apicaixa.exceptions.ResouceNotFoundException;
import com.apiCaixaFinanceiro.apicaixa.repositories.MoedasRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MoedasService {

    private final MoedasRepository moedasRepository;

    public MoedasService(MoedasRepository moedasRepository) {
        this.moedasRepository = moedasRepository;
    }

    @Transactional
    public void GravarMoeda(MoedasEntity moedasEntity) {
        moedasRepository.save(moedasEntity);
    }

    public void somarMoeda(int qte, Long id) {
        var moeda = Optional.of(moedasRepository.findById(id)).get();

        if (moeda.isPresent()){
            moeda.get().setQuantidade(qte);
            moedasRepository.save(moeda.get());
        }
    }

    public List<MoedasEntity> listarMoedas() {
        return moedasRepository.findAll();
    }

    public Optional<MoedasEntity> retonarMoedaId(Long id){
        return Optional.ofNullable(moedasRepository.findById(id).orElseThrow(() -> new BadRequestException("Não foi encontrado dados com esse id")));
    }

}
