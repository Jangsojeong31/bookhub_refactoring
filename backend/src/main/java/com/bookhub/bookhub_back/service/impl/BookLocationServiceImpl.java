package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessage;

import com.bookhub.bookhub_back.entity.BookDisplayLocation;
import com.bookhub.bookhub_back.entity.Book;
import com.bookhub.bookhub_back.entity.Branch;
import com.bookhub.bookhub_back.repository.BookDisplayLocationRepository;
import com.bookhub.bookhub_back.repository.BookRepository;
import com.bookhub.bookhub_back.repository.BranchRepository;
import com.bookhub.bookhub_back.service.BookLocationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookLocationServiceImpl implements BookLocationService {


    
}
