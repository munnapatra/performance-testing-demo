import {
  Component,
  EventEmitter,
  Output
} from '@angular/core';

import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username = '';
  password = '';

  @Output()
  loginSuccess = new EventEmitter<void>();

  login(): void {

    if (
      this.username === 'admin' &&
      this.password === 'admin'
    ) {

      this.loginSuccess.emit();

    } else {

      alert('Invalid username or password');

    }
  }
}