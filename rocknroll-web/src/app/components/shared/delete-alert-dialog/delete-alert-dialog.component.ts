import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DeleteAlertData } from 'src/app/models/DeleteAlertData';

@Component({
  selector: 'app-delete-alert-dialog',
  templateUrl: './delete-alert-dialog.component.html',
  styleUrls: ['./delete-alert-dialog.component.css']
})
export class DeleteAlertDialogComponent {
  data: DeleteAlertData

  constructor(
    @Inject(MAT_DIALOG_DATA) dialogData: DeleteAlertData
  ) {
    this.data = dialogData;
  }

  onClickUndo() {
    window.location.href = "/customs";
  }

  onClickDelete() {
    this.data.delete();
    window.location.href = "/customs";
  }
}
