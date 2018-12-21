// layui方法
var prefix = "/user"
var tableIns
layui.use(['table', 'form', 'layer', 'vip_table'], function () {
        // 操作对象
        var layer = layui.layer
            , table = layui.table
            , vipTable = layui.vip_table
            , $ = layui.$
            , active = {
            reload: function () {
                var username = $('#username');
                //执行重载
                tableIns.reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {
                        username: username.val()
                    }
                });
            }
        };

        // 表格渲染
        tableIns = table.render({
            elem: '#dateTable'                  //指定原始表格元素选择器（推荐id选择器）
            , title: '用户数据表'
            , height: vipTable.getFullHeight()    //容器高度
            , cols: [[                  //标题栏
                {
                    field: 'userId',
                    title: 'ID',
                    align: 'center',
                    width: 80,
                    sort: true,
                    fixed: 'left',
                }
                , {field: 'username', title: '用户名', align: 'center', width: 80}
                , {field: 'phone', title: '手机号', align: 'center', width: 160}
                , {field: 'deptName', title: '所属部门', align: 'center', width: 160}
                , {field: 'createTime', title: '创建时间', align: 'center', width: 250, sort: true}
                , {field: 'delFlag', title: '状态', align: 'center', width: 80, sort: true, templet: '#delFlag'}
                , {fixed: 'right', title: '操作', width: "200", align: 'center', toolbar: '#barOption'} //这里的toolbar值是模板元素的选择器
            ]]
            , id: 'userDataList'
            , url: '/user/userPage/' //数据接口
            , page: true
            , limit: 10
            , limits: [10, 20, 50]
            , done: function (res, curr, count) {
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //console.log(count);
            }
        });

        // 获取选中行
        table.on('checkbox(dataCheck)', function (obj) {
            layer.msg('123');
            console.log(obj.checked); //当前是否选中状态
            console.log(obj.data); //选中行的相关数据
            console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
        });

        //// 刷新
        $('#btn-refresh').on('click', function () {
            tableIns.reload();
        });
        ////新增
        $('#btn-add').on('click', function () {
            addUser();
        });


        //监听行工具事件
        table.on('tool(userTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    $.ajax({
                        url: "/user/remove",
                        type: "post",
                        data: {
                            'id': data.userId
                        },
                        success: function (r) {
                            if (r.code == 0) {
                                layer.msg(r.msg);
                                reLoad();
                            } else {
                                layer.msg(r.msg);
                            }
                        },
                        error: function (request) {
                            layer.alert("Connection error");
                        },
                    });
                });
            }
            else if (obj.event === 'edit') {
                editUser(data.userId)
            } else if (obj.event === "roleAuthorization") {
                roleOpen(data.userId)
            }
        });


        $('.my-btn-box .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    }
);

/**
 * 角色
 */
function roleOpen(id) {
    //页面层-自定义
    layer.open({
        type: 2,
        title: "用户角色",
        closeBtn: false,//关闭按钮
        shift: 2,
        area: ['400px', '500px'],
        //btn: ['新增', '取消'],
        closeBtn: 1,//关闭按钮
        // btnAlign: 'c',
        resize: false,
        //maxmin : true,//最大最小化
        content: "/role/roleTree/" + id,
        success: function (layero, index) {

        },
        cancel: function (index, layero) {
            if (confirm('确定要关闭么')) { //只有当点击confirm框的确定时，该层才会关闭
                layer.close(index)
            }
            return false;
        }
    });
}

/**
 * 新增
 */
function addUser() {
    //页面层-自定义
    layer.open({
        type: 2,
        title: "新增用户",
        closeBtn: false,//关闭按钮
        shift: 2,
        area: ['450px', '400px'],
        //btn: ['新增', '取消'],
        closeBtn: 1,//关闭按钮
        // btnAlign: 'c',
        resize: false,
        //maxmin : true,//最大最小化
        content: prefix + "/add",
        success: function (layero, index) {

        },
        cancel: function (index, layero) {
            if (confirm('确定要关闭么')) { //只有当点击confirm框的确定时，该层才会关闭
                layer.close(index)
            }
            return false;
        }
    });
}

/**
 * 编辑用户
 * @param id
 */
function editUser(id) {
    //页面层-自定义
    layer.open({
        type: 2,
        title: "编辑用户",
        closeBtn: false,//关闭按钮
        shift: 2,
        area: ['450px', '400px'],
        closeBtn: 1,//关闭按钮
        // btnAlign: 'c',
        //maxmin : true,//最大最小化
        content: prefix + "/edit/" + id,
        success: function (layero, index) {

        },
        cancel: function (index, layero) {
            layer.close(index)
            return false;
        }
    });
}

/**
 * 刷新
 */
function reLoad() {
    tableIns.reload();
}
