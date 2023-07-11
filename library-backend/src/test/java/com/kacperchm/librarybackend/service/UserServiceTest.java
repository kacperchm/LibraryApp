package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.InMemoryRepo.InMemoryAddressRepository;
import com.kacperchm.librarybackend.InMemoryRepo.InMemoryUserRepository;
import com.kacperchm.librarybackend.model.Address;
import com.kacperchm.librarybackend.model.Book;
import com.kacperchm.librarybackend.model.LibraryMember;
import com.kacperchm.librarybackend.model.User;
import com.kacperchm.librarybackend.model.dto.BookDto;
import com.kacperchm.librarybackend.model.dto.UserDto;
import com.kacperchm.librarybackend.model.responses.BookResponse;
import com.kacperchm.librarybackend.repository.AddressesRepository;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {
    UsersRepository usersRepository = new InMemoryUserRepository();
    AddressesRepository addressesRepository = new InMemoryAddressRepository();

    @Test
    public void should_return_second_user_data() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(1L,"adrew123", "adrew123@gmail.com", "555777666","andrzej","ROLE_USER", address1, member1);
        usersRepository.save(user1);

        Address address2 = new Address("Kraków", "55-350", "Szkolna", "23/6");
        addressesRepository.save(address1);
        LibraryMember member2 = new LibraryMember("Krystian", "Froń");
        User user2 = new User(2L,"cris111", "cris111@gmail.com", "621874561","cris","ROLE_USER", address2, member2);
        usersRepository.save(user2);

        // when
        UserDto userDto = service.getUserInfo(2L);
        // then
        assertThat(userDto.getName()).isEqualTo("Krystian");
        assertThat(userDto.getCity()).isEqualTo("Kraków");
        assertThat(userDto.getMail()).isEqualTo("cris111@gmail.com");
    }

    @Test
    public void should_return_all_users_data() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(1L,"adrew123", "adrew123@gmail.com", "555777666","andrzej","ROLE_USER", address1, member1);
        usersRepository.save(user1);

        Address address2 = new Address("Kraków", "55-350", "Szkolna", "23/6");
        addressesRepository.save(address2);
        LibraryMember member2 = new LibraryMember("Krystian", "Froń");
        User user2 = new User(2L,"cris111", "cris111@gmail.com", "621874561","cris","ROLE_USER", address2, member2);
        usersRepository.save(user2);

        Address address3 = new Address("Kraków", "55-350", "Parkowa", "121/6");
        addressesRepository.save(address3);
        LibraryMember member3 = new LibraryMember("Jan", "Słoń");
        User user3 = new User(3L,"slon68", "slon68@gmail.com", "625432561","JSlon","ROLE_USER", address3, member3);
        usersRepository.save(user3);

        // when
        List<UserDto> userDtoList = service.getAllUsersInfo();
        // then
        assertThat(userDtoList.size()).isEqualTo(3);
        assertThat(userDtoList.get(0).getMail()).isEqualTo("adrew123@gmail.com");
        assertThat(userDtoList.get(1).getMail()).isEqualTo("cris111@gmail.com");
        assertThat(userDtoList.get(2).getMail()).isEqualTo("slon68@gmail.com");
        assertThat(userDtoList.get(0).getName()).isEqualTo("Andrzej");
        assertThat(userDtoList.get(1).getName()).isEqualTo("Krystian");
        assertThat(userDtoList.get(2).getName()).isEqualTo("Jan");
        assertThat(userDtoList.get(0).getCity()).isEqualTo("Rzeszów");
        assertThat(userDtoList.get(1).getCity()).isEqualTo("Kraków");
        assertThat(userDtoList.get(2).getCity()).isEqualTo("Kraków");
    }
}
