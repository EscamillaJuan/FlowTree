import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

interface HttpMethod {
    get: 'get';
    post: 'post';
    put: 'put';
    delete: 'delete';
}

@Injectable({
    providedIn: 'root'
})
export class BaseService {
    private baseUri = 'http://localhost:8080/api/v1/';
    private http = inject(HttpClient);

    constructor() { }

    protected apiRequest<T>(method: 'get', endpoint: string): Observable<T>;
    protected apiRequest<T>(method: 'post' | 'put', endpoint: string, body: any): Observable<T>;
    protected apiRequest<T>(method: 'delete', endpoint: string): Observable<T>;

    protected apiRequest<T>(method: keyof HttpMethod, endpoint: string, body?: any): Observable<T> {
        const uri = `${this.baseUri}${endpoint}`;

        switch (method) {
            case 'get':
                return this.http.get<T>(uri);
            case 'post':
                return this.http.post<T>(uri, body);
            case 'put':
                return this.http.put<T>(uri, body);
            case 'delete':
                return this.http.delete<T>(uri);
            default:
                throw new Error(`Unsupported HTTP method: ${method}`);
        }
    }
}
