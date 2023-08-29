import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  text: string[] = ['Witaj w naszej wspaniałej bibliotece, miejscu pełnym wiedzy, przygód i inspiracji. Nasza biblioteka to przestrzeń,\n' +
  '      gdzie strony książek ożywają, wprowadzając cię w światy niezliczonych historii, przygód i nauki. Oferujemy szeroki\n' +
  '      wybór książek z różnych gatunków - od klasycznych powieści po najnowsze bestsellery, od głębokich dzieł naukowych\n' +
  '      po fascynujące książki podróżnicze.',
    'Niezależnie od tego, czy jesteś pasjonatem literatury, nauki czy sztuki, nasza biblioteka ma wiele do zaoferowania. ' +
    'Nasze półki są wypełnione skarbami słowa pisanego, które czekają, aby je odkryć. Organizujemy także liczne wydarzenia,\n' +
    ' spotkania autorskie i warsztaty, aby umożliwić wspólne dzielenie się wiedzą i inspiracją.',
    'Nasza misja to nie tylko udostępnianie książek, ale również tworzenie wspólnoty miłośników czytania. Tutaj każdy\n' +
    '      może znaleźć coś dla siebie - od dzieci, które odkrywają świat literatury, po dorosłych szukających chwili relaksu\n' +
    '      w dobrej książce. Zachęcamy do odwiedzenia naszej biblioteki, by doświadczyć magii słowa pisanego\n' +
    '      i być częścią naszej literackiej społeczności.'];

  openingHours: string[]  = [
    'Poniedziałek: 9:00 - 18:00',
    'Wtorek: 9:00 - 18:00',
    'Środa: 9:00 - 18:00',
    'Czwartek: 9:00 - 18:00',
    'Piątek: 9:00 - 18:00',
    'Sobota: 10:00 - 14:00',
    'Niedziela: Zamknięte']
}
