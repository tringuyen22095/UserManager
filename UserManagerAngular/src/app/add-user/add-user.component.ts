import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from "@angular/router";

@Component({
    selector: 'app-add-user',
    templateUrl: './add-user.component.html',
    styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
    newUser: User = new User;
    dob: string;
    constructor(private svr: UserService, private rou: Router) { }
    value: string = "CREATE";
    ngOnInit() {
    }
    addUser() {
        this.newUser.dateOfBirth = new Date(this.dob);
        // let vm = {
        //   "firstName": "Tri3333",
        //   "lastName": "Nguyen",
        //   "dateOfBirth": 811702800000,
        //   "country": "Viet Nam"
        // };
        this.svr.addUser(this.newUser).subscribe(res => {
            let data = res.json();
            // console.log(data)
            if (data && data.callStatus === 'Success') {
                alert('Thanh cong roi');
                this.rou.navigate(['/users']);
            }
            else {
                alert('Loi roi');
                this.rou.navigate(['/addUser']);
            }
        }, err => {
            alert('Loi roi')
        });
    }

}
