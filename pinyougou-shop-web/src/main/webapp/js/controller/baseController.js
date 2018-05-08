//品牌控制层
app.controller('baseController', function ($scope) {

    //重新加载列表 数据
    $scope.reloadList = function () {
        //切换页码
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }

    //分页控件配置
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            $scope.reloadList();//重新加载
        }
    };

    $scope.selectIds = [];//选中的ID集合

    //更新复选
    $scope.updateSelection = function ($event, id) {
        if ($event.target.checked) {//如果是被选中,则增加到数组
            $scope.selectIds.push(id);
            console.log(id);
        } else {
            var idx = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(idx, 1);//删除 
        }
    }

//转换json字符字符串,变为json对象,可以拿到属性值value
    $scope.jsonToString = function (jsonString, key) {
        var json = JSON.parse(jsonString);
        var value = "";
        for (var i = 0; i < json.length; i++) {
            if (i > 0) {
                value += ",";
            }
            //a={"":1,"":""}  因为是一个变量,所以不采取对象.属性方法
            //a.id   a['']
            value += json[i][key];
        }
        return value;
    }
//集合中按照key查询对象,,
// $scope.entity.goodsDesc.specificationItems=[{“attributeName”:”规格名称”,”attributeValue”:[“规格选项 1”,“规格选项 2”.... ] } , .... ]
    $scope.searchObjectByKey = function(list, key, keyvalue) {
        alert(key)
        for(var i=0;i<list.length;i++){
            //因为key是一个变量,如果是一个常量可以直接list[i].key
            if(list[i][key]==keyvalue){
                return list[i];
            }
        }
        return null;
    }
});	