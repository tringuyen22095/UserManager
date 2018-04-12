import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { User } from '../user';
import { Router } from "@angular/router";
import { Location } from '@angular/common';

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
    lstUser: any[] = [];

    constructor(private svr: UserService, private rou: Router, private loc: Location) {
    }

    ngOnInit() {
        this.getListUser();
    }

    getListUser() {
        this.svr.getAllUser().subscribe(res => {
            let data = res.json();
            this.lstUser = data.result;
        }, err => {
            alert('Loi roi')
        });
    }

    deleteUser(id: number, index: number) {
        this.svr.deelteUser(id).subscribe(res => {
            let data = res.json();
            if (data.callStatus === 'Success') {
                alert('Thanh cong');
            } else {
                alert(data.message);
            }
            this.lstUser.splice(index, 1);
        }, err => {
            alert('Loi roi')
        });
    }

    goToAddUser() {
        this.rou.navigate(['/users/addUser']);
    }

    goToDownload() {
        this.rou.navigate(['/dropBox']);
    }

    goToUpdateUser(id: number) {
        this.rou.navigate(['/users/' + id + '/updateUser/']);
    }
}
