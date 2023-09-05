import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../../core/services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  hide = true;
  errorMessage = '';

  registerForm = new FormGroup({
    username: new FormControl('', {
      validators: [Validators.required],
      nonNullable: true,
    }),
    mail: new FormControl('', {
      validators: [
        Validators.required,
        Validators.email,
        Validators.minLength(5),
        Validators.maxLength(50),
      ],
      nonNullable: true,
    }),
    phoneNumber: new FormControl('', {
      validators: [
        Validators.required,
        Validators.minLength(9),
        Validators.maxLength(9),
      ],
      nonNullable: true,
    }),
    password: new FormControl('', {
      validators: [Validators.required],
      nonNullable: true,
    }),
    city: new FormControl('', {
      validators: [
        Validators.required],
      nonNullable: true,
    }),
    zipCode: new FormControl('', {
      validators: [
        Validators.required],
      nonNullable: true,
    }),
    street: new FormControl('', {
      validators: [
        Validators.required],
      nonNullable: true,
    }),
    houseNumber: new FormControl('', {
      validators: [
        Validators.required],
      nonNullable: true,
    }),
    name: new FormControl('', {
      validators: [Validators.required],
      nonNullable: true,
    }),
    surname: new FormControl('', {
      validators: [Validators.required],
      nonNullable: true,
    }),
  });

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {
  }

  get controls() {
    return this.registerForm.controls;
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

  onRegister() {
    const createdUser = this.registerForm.getRawValue();
    this.authService.register(createdUser).subscribe({
      next: (value) => {
        if (value !== null) {
          this.router.navigate(['/logowanie']);
        }
      },
      error: (err) => {
        this.errorMessage = 'Użytkownik już istnieje lub wystąpił błąd serwera.';
      },
    });
  }
}
