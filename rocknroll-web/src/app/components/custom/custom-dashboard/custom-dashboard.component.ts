import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Custom, CustomStore } from 'src/app/models/Custom';
import { DeleteAlertData } from 'src/app/models/DeleteAlertData';
import { CustomService } from 'src/app/services/custom.service';
import { DeleteAlertDialogComponent } from '../../shared/delete-alert-dialog/delete-alert-dialog.component';
import { CustomFormComponent } from '../custom-form/custom-form.component';

@Component({
  selector: 'app-custom-dashboard',
  templateUrl: './custom-dashboard.component.html',
  styleUrls: ['./custom-dashboard.component.css']
})
export class CustomDashboardComponent implements OnInit {

  customStore: CustomStore = {
    store: new Set<Custom>()
  }
  displayCustoms: Custom[] = []
  displayedColumns = ['name', 'command', 'action']

  constructor(
    private readonly customDialog: MatDialog,
    private readonly deleteAlertDialog: MatDialog,
    private customService: CustomService) { }

  ngOnInit(): void {
    this.customStore = this.customService.loadCustoms(this.customStore);
    this.displayCustoms = Array.from(this.customStore.store.values());
  }

  onClickCreateCustom() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = { name: '', command: '' };
    this.customDialog.open(CustomFormComponent, dialogConfig);
  }

  onClickEdit(custom: Custom) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = custom;
    this.customDialog.open(CustomFormComponent, dialogConfig);
  }

  onClickDelete(custom: Custom) {
    const dialogConfig = new MatDialogConfig();
    const deleteAlertData: DeleteAlertData = {
      message: `${custom.name} will be deleted`,
      delete: () => this.customService.deleteCustom(custom),
    };
    dialogConfig.data = deleteAlertData;
    this.deleteAlertDialog.open(DeleteAlertDialogComponent, dialogConfig);
  }
}
