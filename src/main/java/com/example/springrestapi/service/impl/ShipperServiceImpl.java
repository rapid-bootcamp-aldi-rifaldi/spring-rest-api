package com.example.springrestapi.service.impl;

import com.example.springrestapi.entity.ShipperEntity;
import com.example.springrestapi.model.ShipperModel;
import com.example.springrestapi.repository.ShipperRepo;
import com.example.springrestapi.service.ShipperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShipperServiceImpl implements ShipperService {
    private ShipperRepo repo;

    @Autowired
    public ShipperServiceImpl(ShipperRepo repo){
        this.repo = repo;
    }

    @Override
    public List<ShipperModel> getAll() {
        return this.repo.findAll().stream().map(ShipperModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ShipperModel> getById(Long id) {
        if(id == 0) {
            return Optional.empty();
        }
        Optional<ShipperEntity> result = this.repo.findById(id);
        return result.map(ShipperModel::new);
    }

    @Override
    public Optional<ShipperModel> save(ShipperModel model) {
        if(model == null) {
            return Optional.empty();
        }
        ShipperEntity entity = new ShipperEntity(model);
        try {
            this.repo.save(entity);
            return Optional.of(new ShipperModel(entity));
        }catch (Exception e){
            log.error("Save is failed, error: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<ShipperModel> update(Long id, ShipperModel model) {
        if(id == 0) {
            return Optional.empty();
        }

        ShipperEntity result = this.repo.findById(id).orElse(null);
        if(result == null){
            return Optional.empty();
        }

        // copy property
        BeanUtils.copyProperties(model, result);
        try {
            this.repo.save(result);
            return Optional.of(new ShipperModel(result));
        }catch (Exception e){
            log.error("Update is failed, error: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<ShipperModel> delete(Long id) {
        if(id == 0) {
            return Optional.empty();
        }

        ShipperEntity result = this.repo.findById(id).orElse(null);
        if(result == null){
            return Optional.empty();
        }

        try {
            this.repo.delete(result);
            return Optional.of(new ShipperModel(result));
        }catch (Exception e){
            log.error("Delete is failed, error: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
