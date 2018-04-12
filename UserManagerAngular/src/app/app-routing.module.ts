import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UsersComponent } from './users/users.component';
import { DropBoxComponent } from './drop-box/drop-box.component';
import { AddUserComponent } from './add-user/add-user.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { SignInSignUpComponent } from './sign-in-sign-up/sign-in-sign-up.component';

const routes: Routes = [
    { path: '', pathMatch: 'full', redirectTo: 'login' },
    { path: 'login', component: SignInSignUpComponent },
    { path: 'users', component: UsersComponent },
    { path: 'dropBox', component: DropBoxComponent },
    {
        path: 'users', component: AddUserComponent, children:
            [
                { path: 'addUser', component: AddUserComponent }
            ]
    },
    {
        path: 'users/:id', component: UpdateUserComponent, children:
            [
                { path: 'updateUser', component: UpdateUserComponent }
            ]
    },
    { path: '**', pathMatch: 'full', redirectTo: 'login' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { useHash: true })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
