class LocalStorageService {
  setItem(name, data) {
    localStorage.setItem(name, JSON.stringify(data));
  }

  getItem(name) {
    return JSON.parse(localStorage.getItem(name));
  }

  removeItem(name) {
    localStorage.removeItem(name);
  }
}

export default new LocalStorageService();
