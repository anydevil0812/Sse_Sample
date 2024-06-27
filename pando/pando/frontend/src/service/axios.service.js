"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var axios_1 = require("axios");
var AxiosService = /** @class */ (function () {
    function AxiosService() {
    }
    AxiosService.Instance = axios_1.default.create({
        timeout: 10000,
    });
    return AxiosService;
}());
exports.default = AxiosService;
