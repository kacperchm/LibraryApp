package com.kacperchm.librarybackend.service;

import com.kacperchm.librarybackend.InMemoryRepo.InMemoryAddressRepository;
import com.kacperchm.librarybackend.InMemoryRepo.InMemoryUserRepository;
import com.kacperchm.librarybackend.model.*;
import com.kacperchm.librarybackend.model.filter.UserFilter;
import com.kacperchm.librarybackend.repository.AddressesRepository;
import com.kacperchm.librarybackend.repository.UsersRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        User user1 = new User(1L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        Address address2 = new Address("Kraków", "55-350", "Szkolna", "23/6");
        addressesRepository.save(address1);
        LibraryMember member2 = new LibraryMember("Krystian", "Froń");
        User user2 = new User(2L, "cris111", "cris111@gmail.com", "621874561", "cris", "ROLE_USER", address2, member2);
        usersRepository.save(user2);

        // when
        User userDto = service.getUserInfo(2L);
        // then
        assertThat(userDto.getLibraryMember().getName()).isEqualTo("Krystian");
        assertThat(userDto.getAddress().getCity()).isEqualTo("Kraków");
        assertThat(userDto.getMail()).isEqualTo("cris111@gmail.com");
    }

    @Test
    public void should_return_all_users_data() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(1L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        Address address2 = new Address("Kraków", "55-350", "Szkolna", "23/6");
        addressesRepository.save(address2);
        LibraryMember member2 = new LibraryMember("Krystian", "Froń");
        User user2 = new User(2L, "cris111", "cris111@gmail.com", "621874561", "cris", "ROLE_USER", address2, member2);
        usersRepository.save(user2);

        Address address3 = new Address("Kraków", "55-350", "Parkowa", "121/6");
        addressesRepository.save(address3);
        LibraryMember member3 = new LibraryMember("Jan", "Słoń");
        User user3 = new User(3L, "slon68", "slon68@gmail.com", "625432561", "JSlon", "ROLE_USER", address3, member3);
        usersRepository.save(user3);

        // when
        List<User> userDtoList = service.getAllUsersInfo();
        // then
        assertThat(userDtoList.size()).isEqualTo(3);
        assertThat(userDtoList.get(0).getMail()).isEqualTo("adrew123@gmail.com");
        assertThat(userDtoList.get(1).getMail()).isEqualTo("cris111@gmail.com");
        assertThat(userDtoList.get(2).getMail()).isEqualTo("slon68@gmail.com");
        assertThat(userDtoList.get(0).getLibraryMember().getName()).isEqualTo("Andrzej");
        assertThat(userDtoList.get(1).getLibraryMember().getName()).isEqualTo("Krystian");
        assertThat(userDtoList.get(2).getLibraryMember().getName()).isEqualTo("Jan");
        assertThat(userDtoList.get(0).getAddress().getCity()).isEqualTo("Rzeszów");
        assertThat(userDtoList.get(1).getAddress().getCity()).isEqualTo("Kraków");
        assertThat(userDtoList.get(2).getAddress().getCity()).isEqualTo("Kraków");
    }

    @Test
    public void should_return_all_users_where_email_contains_slon() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(1L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        Address address2 = new Address("Kraków", "55-350", "Szkolna", "23/6");
        addressesRepository.save(address2);
        LibraryMember member2 = new LibraryMember("Krystian", "Froń");
        User user2 = new User(2L, "cris111", "cris111@gmail.com", "621874561", "cris", "ROLE_USER", address2, member2);
        usersRepository.save(user2);

        Address address3 = new Address("Kraków", "55-350", "Parkowa", "121/6");
        addressesRepository.save(address3);
        LibraryMember member3 = new LibraryMember("Jan", "Słoń");
        User user3 = new User(3L, "slon68", "slon68@gmail.com", "625432561", "JSlon", "ROLE_USER", address3, member3);
        usersRepository.save(user3);

        Address address4 = new Address("Kraków", "55-350", "Parkowa", "122/6");
        addressesRepository.save(address3);
        LibraryMember member4 = new LibraryMember("Michał", "Słoń");
        User user4 = new User(4L, "slon98", "slon98@gmail.com", "625442523", "JSlon", "ROLE_USER", address4, member4);
        usersRepository.save(user4);

        UserFilter filter = new UserFilter("", "slon", "");
        // when
        List<User> userDtoList = service.getAllFilteredUsersInfo(filter);
        // then
        assertThat(userDtoList.size()).isEqualTo(2);
        assertThat(userDtoList.get(0).getMail()).isEqualTo("slon68@gmail.com");
        assertThat(userDtoList.get(1).getMail()).isEqualTo("slon98@gmail.com");
        assertThat(userDtoList.get(0).getLibraryMember().getName()).isEqualTo("Jan");
        assertThat(userDtoList.get(1).getLibraryMember().getName()).isEqualTo("Michał");
    }

    @Test
    public void should_return_all_users_where_phone_number_contains_62() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(1L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        Address address2 = new Address("Kraków", "55-350", "Szkolna", "23/6");
        addressesRepository.save(address2);
        LibraryMember member2 = new LibraryMember("Krystian", "Froń");
        User user2 = new User(2L, "cris111", "cris111@gmail.com", "621874561", "cris", "ROLE_USER", address2, member2);
        usersRepository.save(user2);

        Address address3 = new Address("Kraków", "55-350", "Parkowa", "121/6");
        addressesRepository.save(address3);
        LibraryMember member3 = new LibraryMember("Jan", "Słoń");
        User user3 = new User(3L, "slon68", "slon68@gmail.com", "635432561", "JSlon", "ROLE_USER", address3, member3);
        usersRepository.save(user3);

        Address address4 = new Address("Kraków", "55-350", "Parkowa", "122/6");
        addressesRepository.save(address3);
        LibraryMember member4 = new LibraryMember("Michał", "Słoń");
        User user4 = new User(4L, "slon98", "slon98@gmail.com", "625442523", "JSlon", "ROLE_USER", address4, member4);
        usersRepository.save(user4);

        UserFilter filter = new UserFilter("", "", "62");
        // when
        List<User> userDtoList = service.getAllFilteredUsersInfo(filter);
        // then
        assertThat(userDtoList.size()).isEqualTo(2);
        assertThat(userDtoList.get(0).getMail()).isEqualTo("cris111@gmail.com");
        assertThat(userDtoList.get(1).getMail()).isEqualTo("slon98@gmail.com");
        assertThat(userDtoList.get(0).getPhoneNumber()).isEqualTo("621874561");
        assertThat(userDtoList.get(1).getPhoneNumber()).isEqualTo("625442523");
    }

    @Test
    public void should_return_all_users_where_phone_number_contains_62_and_username_contains_slon() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(1L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        Address address2 = new Address("Kraków", "55-350", "Szkolna", "23/6");
        addressesRepository.save(address2);
        LibraryMember member2 = new LibraryMember("Krystian", "Froń");
        User user2 = new User(2L, "cris111", "cris111@gmail.com", "621874561", "cris", "ROLE_USER", address2, member2);
        usersRepository.save(user2);

        Address address3 = new Address("Kraków", "55-350", "Parkowa", "121/6");
        addressesRepository.save(address3);
        LibraryMember member3 = new LibraryMember("Jan", "Słoń");
        User user3 = new User(3L, "slon68", "slon68@gmail.com", "625432561", "JSlon", "ROLE_USER", address3, member3);
        usersRepository.save(user3);

        Address address4 = new Address("Kraków", "55-350", "Parkowa", "122/6");
        addressesRepository.save(address3);
        LibraryMember member4 = new LibraryMember("Michał", "Słoń");
        User user4 = new User(4L, "slon98", "slon98@gmail.com", "625442523", "JSlon", "ROLE_USER", address4, member4);
        usersRepository.save(user4);

        UserFilter filter = new UserFilter("slon", "", "62");
        // when
        List<User> userDtoList = service.getAllFilteredUsersInfo(filter);
        // then
        assertThat(userDtoList.size()).isEqualTo(2);
        assertThat(userDtoList.get(0).getMail()).isEqualTo("slon68@gmail.com");
        assertThat(userDtoList.get(1).getMail()).isEqualTo("slon98@gmail.com");
        assertThat(userDtoList.get(0).getUsername()).isEqualTo("slon68");
        assertThat(userDtoList.get(1).getUsername()).isEqualTo("slon98");
        assertThat(userDtoList.get(0).getPhoneNumber()).isEqualTo("625432561");
        assertThat(userDtoList.get(1).getPhoneNumber()).isEqualTo("625442523");
    }

    @Test
    public void should_successfully_change_phone_number() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(2L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        String number = "555999777";
        Long userId = 2L;
        // when
        String message = service.changePhoneNumber(userId, number);
        // then
        assertThat(message).isEqualTo("Number changed successfully");
    }

    @Test
    public void should_return_message_for_nonexistent_user_when_user_change_number() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(2L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        String number = "555999777";
        Long userId = 3L;
        // when
        String message = service.changePhoneNumber(userId, number);
        // then
        assertThat(message).isEqualTo("User does not exist");
    }

    @Test
    public void should_return_message_regarding_wrong_number_format() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(2L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        String number = "1555999777";
        Long userId = 2L;
        // when
        String message = service.changePhoneNumber(userId, number);
        // then
        assertThat(message).isEqualTo("Wrong number format");
    }

    @Test
    public void should_successfully_change_user_role() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(5L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        String role = "ROLE_ADMIN";
        Long userId = 5L;
        // when
        String message = service.changeRole(userId, role);
        // then
        assertThat(message).isEqualTo("Role changed successfully");
    }

    @Test
    public void should_return_message_for_nonexistent_user_while_changing_user_role() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(2L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        String role = "ROLE_ADMIN";
        Long userId = 5L;
        // when
        String message = service.changeRole(userId, role);
        // then
        assertThat(message).isEqualTo("User does not exist");
    }

    @Test
    public void should_successfully_change_password() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(5L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        String old = "andrzej";
        String newPwd = "AAAWWWkc98";
        Long userId = 5L;
        // when
        String message = service.changePassword(userId, old, newPwd);
        // then
        assertThat(message).isEqualTo("Password changed successfully");
        assertThat(usersRepository.findById(5L).get().getPassword()).isEqualTo("AAAWWWkc98");
    }

    @Test
    public void should_return_message_about_wrong_old_password() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(5L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        String old = "andrzej123";
        String newPwd = "AAAWWWkc98";
        Long userId = 5L;
        // when
        String message = service.changePassword(userId, old, newPwd);
        // then
        assertThat(message).isEqualTo("Old password is incorrect");
        assertThat(usersRepository.findById(5L).get().getPassword()).isEqualTo("andrzej");
    }

    @Test
    public void should_return_message_for_nonexistent_user_while_changing_password() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(2L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        String old = "andrzej123";
        String newPwd = "AAAWWWkc98";
        Long userId = 3L;
        // when
        String message = service.changePassword(userId, old, newPwd);
        // then
        assertThat(message).isEqualTo("User does not exist");
        assertThat(usersRepository.existsById(3L)).isEqualTo(false);
    }

    @Test
    public void should_successfully_change_address() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(5L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        Address newAddress = new Address("Kraków", "", "", "");
        Long userId = 5L;
        // when
        String message = service.changeAddress(userId, newAddress);
        // then
        assertThat(message).isEqualTo("Address update pass successfully");
        assertThat(usersRepository.findById(5L).get().getAddress().getCity()).isEqualTo("Kraków");
        assertThat(usersRepository.findById(5L).get().getAddress().getZipCode()).isEqualTo("39-050");
    }

    @Test
    public void should_return_message_for_nonexistent_user_while_updating_address() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(2L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        Address newAddress = new Address("Kraków", "", "", "");
        Long userId = 5L;
        // when
        String message = service.changeAddress(userId, newAddress);
        // then
        assertThat(message).isEqualTo("User does not exist");
        assertThat(usersRepository.findById(2L).get().getAddress().getCity()).isEqualTo("Rzeszów");
    }

    @Test
    public void should_successfully_remove_user() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(1L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        Address address2 = new Address("Kraków", "55-350", "Szkolna", "23/6");
        addressesRepository.save(address2);
        LibraryMember member2 = new LibraryMember("Krystian", "Froń");
        User user2 = new User(2L, "cris111", "cris111@gmail.com", "621874561", "cris", "ROLE_USER", address2, member2);
        usersRepository.save(user2);

        Long userId = 2L;
        // when
        String message = service.removeUser(userId);
        // then
        assertThat(message).isEqualTo("User removed successfully");
        assertThat(usersRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void should_return_message_for_nonexistent_user_while_removing_user() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(1L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        Address address2 = new Address("Kraków", "55-350", "Szkolna", "23/6");
        addressesRepository.save(address2);
        LibraryMember member2 = new LibraryMember("Krystian", "Froń");
        User user2 = new User(2L, "cris111", "cris111@gmail.com", "621874561", "cris", "ROLE_USER", address2, member2);
        usersRepository.save(user2);

        Long userId = 3L;
        // when
        String message = service.removeUser(userId);
        // then
        assertThat(message).isEqualTo("User does not exist");
        assertThat(usersRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void should_return_message_about_borrowed_book_while_removing_user() {
        // given
        UserService service = new UserService(usersRepository, addressesRepository);

        Address address1 = new Address("Rzeszów", "39-050", "Powstańców Warszawy", "12C");
        addressesRepository.save(address1);
        LibraryMember member1 = new LibraryMember("Andrzej", "Duda");
        User user1 = new User(1L, "adrew123", "adrew123@gmail.com", "555777666", "andrzej", "ROLE_USER", address1, member1);
        usersRepository.save(user1);

        Address address2 = new Address("Kraków", "55-350", "Szkolna", "23/6");
        addressesRepository.save(address2);
        LibraryMember member2 = new LibraryMember("Krystian", "Froń");
        List<Borrow> books = new ArrayList<>();
        books.add(new Borrow(member2, new Book(1L, "Stephen King", "To", 2001, "Horror")));
        member2.setNumOfBorrowedBooks(1);
        User user2 = new User(2L, "cris111", "cris111@gmail.com", "621874561", "cris", "ROLE_USER", address2, member2);
        usersRepository.save(user2);
        Long userId = 2L;
        // when
        String message = service.removeUser(userId);
        // then
        assertThat(message).isEqualTo("User cannot be removed because he has borrowed books");
        assertThat(usersRepository.findAll().size()).isEqualTo(2);
    }
}
