import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

// const url: string = 'http://localhost:8080/user/';
const url: string = 'https://tri-dev.herokuapp.com/user/';

@Injectable()
export class LoginService {

    constructor(public http: Http) { }

    signUp(vm: any) {
        return this.http.post(url + "signUp", vm);
    }

    signIn(vm: any) {
        return this.http.post(url + "signIn", vm);
    }

    signInSocial(vm: any) {
        return this.http.post(url + "signInSocial", vm);
    }

}
