import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { ActivatedRoute, Router } from "@angular/router";
import { DatePipe } from '@angular/common';

@Component({
    selector: 'app-update-user',
    templateUrl: './update-user.component.html'
})
export class UpdateUserComponent implements OnInit {
    value: string = 'Update';
    dob: string;
    id: number;
    firstName: string;
    lastName: string;
    country: string;
    isDisabled = false;
    datePipe = new DatePipe("en");

    constructor(private svr: UserService, private rou: Router, private rouActivated: ActivatedRoute) { }

    ngOnInit() {
        this.getUser();
    }
    getUser() {
        this.id = +this.rouActivated.snapshot.paramMap.get('id');
        this.svr.getUser(this.id).subscribe(response => {
            let data = response.json();
            let user = data.result[0];
            this.dob = this.datePipe.transform(user.dateOfBirth, 'yyyy-MM-dd');
            this.lastName = user.lastName;
            this.firstName = user.firstName;
            this.country = user.country;
        }, err => {
            alert('Loi roi')
        });
    }
    updateUser() {
        let vm = {
            "firstName": this.firstName,
            "lastName": this.lastName,
            "country": this.country,
            "dateOfBirth": this.dob
        };
        this.svr.updateUser(vm, this.id).subscribe(res => {
            let data = res.json();
            if (data && data.callStatus === 'Success') {
                alert('Thanh cong roi');
                this.rou.navigate(['/users']);
            }
            else {
                alert('Loi rois');
                this.rou.navigate(['/updateUser']);
            }
        }, err => {
            alert('Loi roi')
        });
    }
}
