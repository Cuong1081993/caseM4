class AppBase {
    static DOMAIN_SERVER = location.origin;

    static API_SERVER = this.DOMAIN_SERVER + '/api';
    static API_CUSTOMER = this.API_SERVER + '/customers';

    static API_PROVINCE = "https://vapi.vnappmob.com/api/province";

    static API_AUTH =this.API_SERVER + '/auth';

    static  API_REGISTER = this.API_AUTH + '/register';

    static API_LOGIN = this.API_AUTH + '/login';
    static API_CLOUDINARY = 'https://res.cloudinary.com/dkeilyism/image/upload';
    static SCALE_IMAGE_W_80_H_80_Q_100 = 'c_limit,w_80,h_80,q_100';
    static SCALE_IMAGE_W_80_H_80_Q_85 = 'c_limit,w_80,h_80,q_85';
}
class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
}
class Customer {
    constructor(id, customerAvatar, fullName, email, phone, locationRegion, balance) {
        this.id = id;
        this.customerAvatar = customerAvatar;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.locationRegion = locationRegion;
        this.balance = balance;
    }
}
class CustomerAvatar {
    constructor(id, fileFolder, fileName, fileUrl) {
        this.id = id;
        this.fileFolder = fileFolder;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

}

