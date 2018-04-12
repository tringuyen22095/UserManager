import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { User } from './user';

// const url: string = 'http://localhost:8080/employee/';
const url: string = 'https://tri-dev.herokuapp.com/employee/';

@Injectable()
export class UserService {

    private headers: Headers;

    constructor(public http: Http) {
        this.headers = new Headers();
        this.headers.append("Content-Type", "application/json");
    }

    getAllUser() {
        this.headers.delete("Authorization");
        this.headers.append("Authorization", "Bearer " + this.getToken());
        return this.http.get(url, { headers: this.headers });
    }

    getUrl() {
        this.headers.delete("Authorization");
        this.headers.append("Authorization", "Bearer " + this.getToken());
        return this.http.get(url + "getUrl", { headers: this.headers });
    }

    upload(vm) {
        this.headers.delete("Authorization");
        this.headers.append("Authorization", "Bearer " + this.getToken());
        return this.http.post(url + "upload", vm, { headers: this.headers });
    }

    download(vm) {
        this.headers.delete("Authorization");
        this.headers.append("Authorization", "Bearer " + this.getToken());
        return this.http.post(url + "download", vm, { headers: this.headers });
    }

    addUser(vm: any) {
        this.headers.delete("Authorization");
        this.headers.append("Authorization", "Bearer " + this.getToken());
        return this.http.post(url, vm, { headers: this.headers });
    }

    getUser(id: number) {
        this.headers.delete("Authorization");
        this.headers.append("Authorization", "Bearer " + this.getToken());
        return this.http.get(url + id, { headers: this.headers });
    }

    updateUser(user: any, id: number) {
        // let vm: any = {
        //   "firstName": user.firstName,
        //   "lastName": user.lastName,
        //   "country": user.country,
        //   "dateOfBirth": user.dateOfBirth
        // };
        this.headers.delete("Authorization");
        this.headers.append("Authorization", "Bearer " + this.getToken());
        return this.http.put(url + id, user, { headers: this.headers });

    }

    deelteUser(id: number) {
        this.headers.delete("Authorization");
        this.headers.append("Authorization", "Bearer " + this.getToken());
        return this.http.delete(url + id, { headers: this.headers });
    }

    private getToken(): string {
        let t = localStorage.getItem("CURRENT_TOKEN");
        console.log(t);

        return t;
    }
}
