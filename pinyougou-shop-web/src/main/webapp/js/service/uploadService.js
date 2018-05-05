//文件上传的服务层
app.service("uploadService",function ($http) {
    this.uploadFile=function () {
        //html5中独有的formdate,表单数据上传数据
        var formData = new FormData();
        formData.append("file", file.files[0]); //file文件上传框的name,表示第一个输入框中的数据
        return $http({
            method: 'POST',
            url: "../upload.action",
            data: formData,
            headers: {'Content-Type': undefined}, //默认是josn数据
            transformRequest: angular.identity //将序列化我们的 formdata object.
        });
    }
})