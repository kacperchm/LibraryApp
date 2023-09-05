# LibraryApp
## Autor: Kacper Chmura

### Jest to system biblioteczny, który umożliwia efektywnie zarządzać zasobami bibliotecznymi i usprawnia obsługę czytelników.

### Główne cechy i funkcje aplikacji do zarządzania biblioteką:
* **Dodawanie nowych materiałów do bazy danych biblioteki.**
* **Wypożyczanie i zwracanie materiałów bibliotecznych.**
* **Przeszukiwanie katalogu bibliotecznego.**
* **Tworzenie i zarządzanie kartami czytelnika.**

### Ważne informacje:
* **W celu konfiguracji dostępu do bazy danych należy skopiować plik db-example.properties nadać nazwę db.properties i ustawić prawidłowe dane.**
* **Aplikacja w zależności od rodzaju zalogowanego użytkownika umożliwia różne funkcjonalności.**
* **Po pierwszym uruchomieniu aplikacji dostępne jest konto administratora. Mail: admin@gmail.pl Hasło: haslo123 **

### Opis aplikacji:
**Po uruchomieniu backend'u i frontend'u przy podstawowym ustawieniu należy przejść na stronę http://localhost:4200/, gdzie wyświetli się strona główna na której znajdują się podstawowe informacje na temat biblioteki. Na toolbar'ze mamy dostępne opcje przejścia do logowania lub przekierowania na stronę główną. Po naciśnięciu button'a Logowanie nastąpi przekierowanie na stronę http://localhost:4200/logowanie, gdzie widoczna będzie formatka logowania. Poniżej przycisku Zaloguj znajduje się opcja rejestracji, gdzie możemy utworzyć nowego użytkownika. 
Po zalogowaniu jako administrator będziemy mieli dostęp do takich opcji jak:**
* **Po naciśnięciu button'a Książki możliwe jest wyświetlenie książek znajdujących się w bibliotece oraz ich wypożyczenie zarówno dla siebie jak i różynch użytkowników**
* **Po naciśnięciu button'a Dodaj Książkę możliwe jest dodanie nowej książki**
* **Po naciśnięciu button'a Użytkownicy możliwe jest wyświetlenie zarejestrowanych użytkowników, sprawdzenie szczegółowych informacji na temat kont. Na stronie szczegółów będzie możlwość edycji danych użytkownika oraz wyświetlenie wypożyczonych książek**
* **Po naciśnięciu button'a Moje Konto możliwe jest sprawdzenie szczegółowych informacji na temat swojego konta.**
* **Po naciśnięciu button'a Wyloguj zostaniemy wylogowani i przekierowani na stronę logowania.**

**Po zalogowaniu jako użytkownik będziemy mieli dostęp do takich opcji jak:**
* **Po naciśnięciu button'a Książki możliwe jest wyświetlenie książek znajdujących się w bibliotece oraz ich wypożyczenie**
* **Po naciśnięciu button'a Moje Konto możliwe jest sprawdzenie szczegółowych informacji na temat swojego konta. Na stronie szczegółów będzie możlwość edycji swoich danych oraz wyświetlenie wypożyczonych książek**
* **Po naciśnięciu button'a Wyloguj zostaniemy wylogowani i przekierowani na stronę logowania.**

### Technologie użyte w projekcie:
* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)  
* ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) 
* ![Hibernate](https://img.shields.io/static/v1?style=for-the-badge&message=Hibernate&color=59666C&logo=Hibernate&logoColor=FFFFFF&label=) 
* ![MicrosoftSQLServer](https://img.shields.io/badge/Microsoft%20SQL%20Sever-CC2927?style=for-the-badge&logo=microsoft%20sql%20server&logoColor=white)
* ![TypeScript](https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white)
* ![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white) 
* ![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
* ![Angular](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)
