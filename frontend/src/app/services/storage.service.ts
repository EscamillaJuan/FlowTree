import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class StorageService {
    private _cache: string | null = null;
    private SECRET_KEY = "SECRET-KEY";

    setToken(token: string) {
        if (!window) {
            return;
        }
        this._cache = token;
        window.localStorage.setItem(this.SECRET_KEY, token);
    }

    getToken(): string | null | void {
        if (!window) {
            return;
        }
        if (!this._cache) {
            this._cache = window.localStorage.getItem(this.SECRET_KEY);
        }
        return this._cache;
    }

    clearToken() {
        if (!window) {
            return;
        }
        this._cache = null;
        window.localStorage.removeItem(this.SECRET_KEY);
    }
}
