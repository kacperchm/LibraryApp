import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../core/services/auth.service";
import {Router} from "@angular/router";
import {Book, Category} from "../core/models/book.model";
import {PostNumberForm} from "../core/models/user.model";
import {UsersService} from "../core/services/users.service";
import {BooksService} from "../core/services/books.service";

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent {
  errorMessage = '';
  selectedValue!: string;
  categories: Category[] = [
    {value: 'Kryminał i sensacja', viewValue: 'Kryminał i sensacja'},
    {value: 'Fantasy', viewValue: 'Fantasy'},
    {value: 'Science fiction', viewValue: 'Science fiction'},
    {value: 'Romans', viewValue: 'Romans'},
    {value: 'Literatura obyczajowa', viewValue: 'Literatura obyczajowa'},
    {value: 'Historia', viewValue: 'Historia'},
    {value: 'Nauki przyrodnicze i popularnonaukowe', viewValue: 'Nauki przyrodnicze i popularnonaukowe'},
    {value: 'Horror', viewValue: 'Horror'},
    {value: 'Przygodowe', viewValue: 'Przygodowe'},
    {value: 'Biografie', viewValue: 'Biografie'},
    {value: 'Autobiografie', viewValue: 'Autobiografie'},
    {value: 'Poradniki', viewValue: 'Poradniki'},
    {value: 'Komiksy', viewValue: 'Komiksy'},
  ];

  bookForm = new FormGroup({
    author: new FormControl('', {nonNullable: true,
      validators: [
        Validators.required,
      ],
    }),
    title: new FormControl('', {nonNullable: true,
      validators: [
        Validators.required,
      ],
    }),
    publicationYear: new FormControl(2000, {nonNullable: true,
      validators: [
        Validators.required,
        Validators.maxLength(4),
        Validators.minLength(4),
      ],
    }),
    category: new FormControl('', {nonNullable: true,
      validators: [
        Validators.required,
      ],
    }),
  });

  constructor(
    private router: Router,
    private booksService: BooksService,
  ) {}

  get controls() {
    return this.bookForm.controls;
  }

  getErrorMessage(control: FormControl) {
    if (control.hasError('required')) {
      return 'Musisz wpisać jakąś wartość.';
    }

    if (control.hasError('minlength')) {
      return 'Przekazałeś za mało znaków.';
    }

    if (control.hasError('maxlength')) {
      return 'Przekazałeś za dużo znaków.';
    }

    return control.hasError('email') ? 'Nieprawidłowy adres e-mail.' : '';
  }

  onAddBook() {
    const formValues = this.bookForm.getRawValue();
    let book = new Book(0, formValues.author, formValues.title, formValues.publicationYear, formValues.category, true)
    this.booksService.addBook(book).subscribe({
      next: (value) => {
        if(value !== null){
          this.router.navigate(['/']);
        }
      },
      error: (err) => {
        this.errorMessage = 'Wystąpił błąd serwera.';
      },
    });
  }

}
