import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import { Router } from "@angular/router";
import {
    AuthService,
    FacebookLoginProvider,
    GoogleLoginProvider
} from 'angular5-social-login';

@Component({
    selector: 'app-sign-in-sign-up',
    templateUrl: './sign-in-sign-up.component.html',
    styleUrls: ['./sign-in-sign-up.component.css']
})
export class SignInSignUpComponent implements OnInit {

    vm = {};
    constructor(private svr: LoginService, private rou: Router, private svrAuth: AuthService) { }

    ngOnInit() {
    }

    public SignIn() {
        this.svr.signIn(this.vm).subscribe(response => {
            let data = response.json();
            if (data.callStatus == "Fail") {
                alert(data.message);
            } else {
                //login complete, save token to local
                localStorage.removeItem("CURRENT_TOKEN");
                localStorage.setItem("CURRENT_TOKEN", "Bearer " + data.result);
                this.rou.navigate(['/users']);
            }
        }, err => {
            alert('Server có vấn đề!');
        });
    }

    public socialSignIn(socialPlatform: string) {
        let socialPlatformProvider;
        if (socialPlatform == "facebook") {
            socialPlatformProvider = FacebookLoginProvider.PROVIDER_ID;
        } else if (socialPlatform == "google") {
            socialPlatformProvider = GoogleLoginProvider.PROVIDER_ID;
        }
        this.svrAuth.signIn(socialPlatformProvider).then(
            (userData) => {
                //console.log(socialPlatform + " sign in data : ", userData);
                let data = {
                    "email": userData.email,
                    "id": userData.id,
                    "idToken": userData.idToken,
                    "image": userData.image,
                    "name": userData.name,
                    "provide": userData.provider,
                    "token": userData.token
                };
                console.log(data);
                this.svr.signInSocial(data).subscribe(res => {
                    let data = res.json();
                    if (data.callStatus == "Fail") {
                        alert(data.message);
                    } else {
                        //login complete, save token to local
                        localStorage.removeItem("CURRENT_TOKEN");
                        localStorage.setItem("CURRENT_TOKEN", "Bearer " + data.result);
                        this.rou.navigate(['/users']);
                    }
                });
            }
        );
    }

    public SignUp() {
        this.svr.signUp(this.vm).subscribe(response => {
            let data = response.json();
            if (data.callStatus == "Fail") {
                alert("Sign Up Fail");
            } else {

            }
        }, err => {
            alert('Loi roi');
        });
    }
}
