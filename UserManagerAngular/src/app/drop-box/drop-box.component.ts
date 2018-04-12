import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

@Component({
    selector: 'app-drop-box',
    templateUrl: './drop-box.component.html',
    styleUrls: ['./drop-box.component.css']
})
export class DropBoxComponent implements OnInit {
    vm = {};
    lstUrl;
    constructor(private svr: UserService) { }

    ngOnInit() {
        this.getUrl();
    }

    getUrl() {
        this.svr.getUrl().subscribe(res => {
            let data = res.json();
            if (data != null && data.callStatus === "Success") {
                this.lstUrl = data.result;
            } else {
                alert("Server error!");
            }
        });
    }

    download(fileName) {
        let req = { "fileName": fileName };
        this.svr.download(req).subscribe(res => {
            let data = res.json();
            if (data != null && data.callStatus === "Success") {

            } else {
                alert("Serve error!");
            }
        });
    }
    upload() {
        this.svr.upload(this.vm).subscribe(res => {
            let data = res.json();
            if (data != null && data.callStatus === "Success") {
                this.getUrl();
            } else {
                alert("Serve error!");
            }
        });
    }
}
