import { Injectable } from '@angular/core';
import { Custom, CustomStore } from '../models/Custom';

@Injectable({
  providedIn: 'root',
})
export class CustomService {
  getCustom(name: string): Custom {
    const customString = localStorage.getItem(name.toLowerCase());
    if (customString != null) {
      return JSON.parse(customString) as Custom;
    }
    throw new Error(`Custom command ${name} does not exist`);
  }

  storeCustom(custom: Custom) {
    localStorage.setItem(custom.name.toLowerCase(), JSON.stringify(custom));
  }

  deleteCustom(custom: Custom) {
    localStorage.removeItem(custom.name.toLowerCase());
  }

  loadCustoms(customStore: CustomStore): CustomStore {
    const keys = Object.keys(localStorage);
    keys.map((key) => customStore.store.add(this.getCustom(key)));
    return customStore;
  }
}
