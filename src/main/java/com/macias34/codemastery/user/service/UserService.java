package com.macias34.codemastery.user.service;

import com.macias34.codemastery.auth.dto.SignInDto;
import com.macias34.codemastery.auth.dto.SignUpDto;
import com.macias34.codemastery.exception.NoPermissionException;
import com.macias34.codemastery.exception.ResourceAlreadyExistsException;
import com.macias34.codemastery.exception.ResourceNotFoundException;
import com.macias34.codemastery.user.dto.InvoiceDetailsDto;
import com.macias34.codemastery.user.dto.PersonalDetailsDto;
import com.macias34.codemastery.user.dto.UpdateInvoiceDetailsDto;
import com.macias34.codemastery.user.dto.UpdatePersonalDetailsDto;
import com.macias34.codemastery.user.dto.UpdateUserDto;
import com.macias34.codemastery.user.dto.UserDto;
import com.macias34.codemastery.user.entity.InvoiceDetailsEntity;
import com.macias34.codemastery.user.entity.PersonalDetailsEntity;
import com.macias34.codemastery.user.entity.UserEntity;
import com.macias34.codemastery.user.entity.UserRole;
import com.macias34.codemastery.user.mapper.InvoiceDetailsMapper;
import com.macias34.codemastery.user.mapper.PersonalDetailsMapper;
import com.macias34.codemastery.user.mapper.UserMapper;
import com.macias34.codemastery.user.model.UserFilter;
import com.macias34.codemastery.user.repository.InvoiceDetailsRepository;
import com.macias34.codemastery.user.repository.PersonalDetailsRepository;
import com.macias34.codemastery.user.repository.UserRepository;
import com.macias34.codemastery.util.DtoValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class UserService {

    private static final String USER_EXISTS_MESSAGE = "User with username %s already exists.";
    private static final String EMAIL_EXISTS_MESSAGE = "User with email %s already exists.";
    private static final String USER_DOESNT_EXIST_MESSAGE = "User with username %s doesn't exist.";

    private UserRepository userRepository;
    private InvoiceDetailsRepository invoiceDetailsRepository;
    private PersonalDetailsRepository personalDetailsRepository;

    private UserMapper userMapper;
    private InvoiceDetailsMapper invoiceDetailsMapper;
    private PersonalDetailsMapper personalDetailsMapper;

    private PersonalDetailsService personalDetailsService;
    private InvoiceDetailsService invoiceDetailsService;

    public List<UserDto> getAllUsers(UserFilter userFilter) {
        List<UserEntity> userEntities = userRepository.findAll();

        Predicate<UserEntity> conditions = e -> true;

        if (userFilter.getRole() != null) {
            conditions = conditions.and(u -> u.getRole() == userFilter.getRole());
        }

        if (userFilter.getUsername() != null) {
            conditions = conditions
                    .and(u -> u.getUsername().toLowerCase().contains(userFilter.getUsername().toLowerCase()));
        }

        if (userFilter.getEmail() != null) {
            conditions = conditions.and(u -> u.getEmail().toLowerCase().contains(userFilter.getEmail().toLowerCase()));
        }

        return userEntities.stream().filter(conditions).map(userMapper::fromEntityToDto).toList();
    }

    public UserDto getUserById(int id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.fromEntityToDto(user);
    }

    public UserDto getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.fromEntityToDto(user);
    }

    @Transactional
    public UserDto updateUser(int id, UpdateUserDto dto, String loggedUserUsername) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UserEntity loggedUser = userRepository.findByUsername(loggedUserUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (loggedUser.getId() != user.getId() && loggedUser.getRole() != UserRole.ADMIN) {
            throw new NoPermissionException("You cannot update others' user data");
        }

        if (dto.getPersonalDetails() != null) {
            UpdatePersonalDetailsDto personalDetailsDto = dto.getPersonalDetails();
            DtoValidator.validate(personalDetailsDto);

            PersonalDetailsEntity personalDetails = personalDetailsService
                    .updatePersonalDetails(user.getPersonalDetails().getId(), personalDetailsDto);
            user.setPersonalDetails(personalDetails);

            // TODO Rethink should update invoiceDetails when
            // invoiceDetailsSameAsPersonal=true even if personalDetails are empty (getting
            // previuos val)

            if (dto.isInvoiceDetailsSameAsPersonal()) {
                UpdateInvoiceDetailsDto invoiceDetailsDto = UpdateInvoiceDetailsDto.builder()
                        .firstName(personalDetails.getFirstName())
                        .lastName(personalDetails.getLastName())
                        .postalCode(personalDetails.getPostalCode())
                        .street(personalDetails.getStreet())
                        .city(personalDetails.getCity())
                        .phoneNumber(personalDetails.getPhoneNumber()).build();

                InvoiceDetailsEntity invoiceDetails = invoiceDetailsService
                        .updateInvoiceDetails(user.getInvoiceDetails().getId(), invoiceDetailsDto);
                user.setInvoiceDetails(invoiceDetails);
            }
        }

        if (dto.getInvoiceDetails() != null) {
            UpdateInvoiceDetailsDto invoiceDetailsDto = dto.getInvoiceDetails();
            DtoValidator.validate(invoiceDetailsDto);

            InvoiceDetailsEntity invoiceDetails = invoiceDetailsService
                    .updateInvoiceDetails(user.getInvoiceDetails().getId(), invoiceDetailsDto);
            user.setInvoiceDetails(invoiceDetails);
        }

        DtoValidator.validate(dto);

        if (dto.getUsername() != null) {
            checkIfUserExists(SignUpDto.builder().username(dto.getUsername()).build());
            user.setUsername(dto.getUsername());
        }

        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        if (dto.getEmail() != null) {
            checkIfUserExists(SignUpDto.builder().email(dto.getEmail()).build());
            user.setEmail(dto.getEmail());
        }

        if (dto.getNote() != null) {
            user.setNote(dto.getNote());
        }

        userRepository.save(user);

        return userMapper.fromEntityToDto(user);
    }

    public void checkIfUserExists(SignUpDto signUpDto) throws ResourceAlreadyExistsException {
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new ResourceAlreadyExistsException(
                    String.format(USER_EXISTS_MESSAGE, signUpDto.getUsername()));
        }

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    String.format(EMAIL_EXISTS_MESSAGE, signUpDto.getEmail()));
        }
    }

    public void checkIfUserDoesntExist(SignInDto signInDto) throws ResourceNotFoundException {
        if (!userRepository.existsByUsername(signInDto.getUsername())) {
            throw new ResourceNotFoundException(
                    String.format(USER_DOESNT_EXIST_MESSAGE, signInDto.getUsername()));
        }
    }
}
