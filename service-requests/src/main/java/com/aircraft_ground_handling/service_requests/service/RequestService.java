package com.aircraft_ground_handling.service_requests.service;

import com.aircraft_ground_handling.service_requests.repo.RequestRepo;
import com.aircraft_ground_handling.service_requests.repo.model.Request;
import com.aircraft_ground_handling.service_requests.repo.model.RequestStatus;
import com.aircraft_ground_handling.service_requests.repo.model.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RequestService {
    public final RequestRepo requestRepo;

    public List<Request> getAll() {
        List<Request> requests = requestRepo.findAll();
        return requests;
    }

    public Request getByID(int id) throws IllegalArgumentException {
        Optional<Request> request = requestRepo.findById(id);

        if (request.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            return request.get();
        }
    }

    public int create(RequestType requestType, int details, int producer, int consumer, RequestStatus requestStatus)
    throws IllegalArgumentException {
        Request request = new Request(requestType, details, producer, consumer, requestStatus);
        Request savedRequest = requestRepo.save(request);

        return savedRequest.getId();
    }

    public void update(int requestID, RequestStatus requestStatus, int consumerID) throws IllegalArgumentException {
        Request request = getByID(requestID);
        if (requestStatus != null) request.setRequestStatus(requestStatus);
        if (consumerID != 0) request.setConsumer(consumerID);
        requestRepo.save(request);
    }

    public void delete(int id) {
        try {
            requestRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return;
        }
    }
}
