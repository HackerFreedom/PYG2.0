<!--	<script type="text/javascript">
//定义一个模块(引入pagination模块)
var app= angular.module('pinyougou',['pagination']);
//定义一个控制器,注入一个对象,以获取属性和方法,注入一个请求,以向服务器获取数据,来展示给客户端
app.controller('brandController',function ($scope,$http) {
    //读取所有的列表数据,
    $scope.findAll=function () {
        //向后台请求数据
        $http.get('../brand/findAll.action').success(
            function (response) {
                $scope.list=response;
            }
        );
    }

    //重新加载列表数据,
    $scope.reloadList=function () {
        //刷新 列表.切换页码  (当前页和当前页的条数)
        $scope.search($scope.paginationConf.currentPage,
            $scope.paginationConf.itemsPerPage)

    }
    //分页控件, 配置
    //currentPage :当前页  totallItems: 总记录数  itemsPerPage:每页的记录数
    //perPageOptions :分页的选项,,onChange: 当页吗变更后触发的方法
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            //重新加载,数据
            $scope.reloadList();
        }
    }

    //分页功能
    $scope.findPage=function (page,size) {
        $http.get('../brand/findPage.action?page='+page+'&size='+size).success(
            function (response) {
                $scope.list=response.rows;//显示当前的页,,数据,list集合
                $scope.paginationConf.totalItems=response.total;//更新总记录数,框架来分页
            })
    }





    /**
     * 改变brand的值,保存和修改
     */
    $scope.save =function () {
        var methodName='addBrand'
        if($scope.entity.id !=null){
            //如果存在id说明,处于修改状态(平常情况,我们是在form表单中添加隐藏id hide)
            methodName="updateBrand";
        }
        $http.post("../brand/"+methodName+".action",$scope.entity).success(
            function (response) {
                console.log(response)
                //response是一个对象,就是我们封装好的数据,产生的对象
                //post请求每一个输入框,绑定一个变量,由于是双向绑定的所以自动封装到entity中去了
                //console.log(response.type);
                if(response.type==0){
                    $scope.reloadList();
                    alert(response.msg)
                }else{
                    //console.log(response.type)
                    alert(response.msg)
                }
            });
    }
    /**
     * 修改数据
     * save
     */
    /**
     * 查询fingone
     * id
     * 通过click 方法entity.id传过来的id
     */
    $scope.findOne=function (id) {
        $http.get("../brand/findOne.action?id="+id).success(
            function (response) {
                //赋值给,绑定的model中.
                console.log(response)
                $scope.entity=response;
            }
        )
    }
    //获取当前选中的复选框id
    $scope.selectIds=[];
    $scope.updateSelection=function ($event,id) {
        //如果选中当前的复选框,就添加到集合中去
        if($event.target.checked){
            $scope.selectIds.push(id);
        }else {
            var idx=$scope.selectIds.indexOf(id);

            $scope.selectIds.splice(idx,2);//删除的基数
        }
    }
    /**
     * 批量删除数据
     */
    $scope.delete=function () {
        //获取选中的复选框
        $http.get("../brand/deleteBrand.action?ids="+$scope.selectIds).success(
            function (response) {
                if(response.type==0){
                    //删除成功
                    $scope.reloadList();
                    alert(response.msg)
                }else {
                    alert(response.msg);
                }
            })
    }

    /**
     * 条件的查询
     * 基于首字母和name的模糊查询
     */
    //搜索对象
    $scope.searchEntity={};

    $scope.search=function (page,rows) {
        $http.post("../brand/searchBrand.action?page="+page+"&rows="+
            rows,$scope.searchEntity).success(
            function (response) {
                $scope.paginationConf.totalItems=response.total;//总记录数
                $scope.list=response.rows;//给列表变量赋值
            })
    }
})
</script>-->