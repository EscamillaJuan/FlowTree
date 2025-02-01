import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [ReactiveFormsModule],
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss'
})
export class LoginComponent {
    loginForm: FormGroup;
    authService = null;
    errMessage = "";
    constructor(private formBuilder: FormBuilder) {
        this.loginForm = this.formBuilder.group({
            email: ["", [Validators.required, Validators.email]],
            password: ["", [Validators.required, Validators.minLength(6)]],
        });
    }

    onSubmit() {
        if (this.loginForm.valid) {
            console.log(this.loginForm);
        } else {
            this.errMessage = "Please fill in all the required fields.";
        }
    }

}
