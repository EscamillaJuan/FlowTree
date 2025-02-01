import { inject, Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { catchError, tap, throwError } from 'rxjs';
import { StorageService } from './storage.service';
import { Router } from '@angular/router';

type TokenResponse = {
    token: string
}

@Injectable({
    providedIn: 'root'
})
export class AuthService extends BaseService {
    private storageService = inject(StorageService);
    private router = inject(Router);
    login(email: string, password: string) {
        this.apiRequest<TokenResponse>("post", "/auth/login", {
            email: email,
            password: password
        }).pipe(
            tap(response => {
                this.storageService.setToken(response.token);
            }),
            catchError(err => {
                return throwError(
                    () => new Error("Incorrect email or password")
                )
            })
        )
    }

    isLoggedIn() {
        return Boolean(this.storageService.getToken());
    }
}
