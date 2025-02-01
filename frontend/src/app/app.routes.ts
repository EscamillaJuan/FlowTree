import { Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { authGuard } from './guards/auth.guard';
import { LoginComponent } from './components/login/login.component';

export const routes: Routes = [
    { path: '', component: AppComponent, canActivate: [authGuard] },
    { path: 'login', component: LoginComponent }
];
