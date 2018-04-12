import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';

import { AppComponent } from './app.component';
import { UsersComponent } from './users/users.component';
import { AddUserComponent } from './add-user/add-user.component';
import { UserService } from './user.service';
import { LoginService } from './login.service';

import { AppRoutingModule } from './app-routing.module';
import { UpdateUserComponent } from './update-user/update-user.component';
import { SignInSignUpComponent } from './sign-in-sign-up/sign-in-sign-up.component';
import { DropBoxComponent } from './drop-box/drop-box.component';
import { SocialLoginModule, AuthServiceConfig, GoogleLoginProvider, FacebookLoginProvider } from "angular5-social-login";

@NgModule({
    declarations: [
        AppComponent,
        UsersComponent,
        AddUserComponent,
        UpdateUserComponent,
        SignInSignUpComponent,
        DropBoxComponent
    ],
    imports: [
        BrowserModule,
        HttpModule,
        SocialLoginModule,
        FormsModule,
        BsDatepickerModule.forRoot(),
        AppRoutingModule
    ],
    providers: [
        UserService,
        LoginService,
        {
            provide: AuthServiceConfig,
            useFactory: getAuthServiceConfigs
        }
    ],
    bootstrap: [AppComponent]
})

export class AppModule { }

export function getAuthServiceConfigs() {
    let config = new AuthServiceConfig(
        [
            {
                id: FacebookLoginProvider.PROVIDER_ID,
                provider: new FacebookLoginProvider("364101347416669")
            },
            {
                id: GoogleLoginProvider.PROVIDER_ID,
                provider: new GoogleLoginProvider("981952863337-nbg8mssjs63keggn1562ssecv38c1kv0.apps.googleusercontent.com")
            }
        ]
    );
    return config;
}