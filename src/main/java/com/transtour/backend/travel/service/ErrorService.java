package com.transtour.backend.travel.service;

import com.github.dozermapper.core.Mapper;
import com.transtour.backend.travel.dto.ErrorDTO;
import com.transtour.backend.travel.model.LoginError;
import com.transtour.backend.travel.repository.ILoginErrorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;

@Service
public class ErrorService {

    private static Logger log = LoggerFactory.getLogger(ErrorService.class);

    @Autowired
    private Mapper mapper;

    @Autowired
    @Qualifier("repoError")
    private ILoginErrorRepository repository;

    @Transactional
    public CompletableFuture<LoginError> insert (ErrorDTO errorDTO) {

        CompletableFuture<LoginError> cf1 = CompletableFuture.supplyAsync(() -> {

            LoginError newError = new LoginError();
            newError.setTime(LocalTime.now().toString());
            newError.setDateCreated(LocalDate.now().toString());
            newError.setStackTrace(errorDTO.getStackTrace());
            newError.setRepoName(errorDTO.getRepoName());
            newError.setComment(errorDTO.getComment());
            repository.save(newError);
            return newError;
        });

        return cf1;
    }
}
