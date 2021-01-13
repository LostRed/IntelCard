//数据缓存数组
let sectionList = [];
let roleList = [];
let userStateList = [];
let cardStateList = [];
let applyStateList = [];
let applyUserList = [];
let doctorList = [];
let workUserList = [];
let paramTypeList = [];

/**
 * 查找科室
 */
function refreshSectionList() {
    $.ajax({
        url: 'param.do?method=findByType',
        type: 'post',
        dataType: 'json',
        data: {
            paramType: "科室"
        },
        success: function (resp) {
            if (resp.state === 0) {
                sectionList = resp.data;
                loadingParamSelect($('#section'), sectionList);
                loadingParamSelect($('#insert-section'), sectionList);
                loadingParamSelect($('#update-section'), sectionList);
            }
        }
    });
}

/**
 * 查找角色
 */
function refreshRoleList() {
    $.ajax({
        url: 'role.do?method=findAll',
        type: 'post',
        dataType: 'json',
        data: {},
        success: function (resp) {
            if (resp.state === 0) {
                roleList = resp.data;
                loadingRoleSelect($('#role'));
                loadingRoleSelect($('#insert-role'));
                loadingRoleSelect($('#update-role'));
            }
        }
    });
}

/**
 * 查找用户状态
 * @param paramValue 0表示全部状态，1表示未删除的状态
 */
function refreshUserStateList(paramValue) {
    $.ajax({
        url: 'param.do?method=findUserState',
        type: 'post',
        dataType: 'json',
        data: {
            paramValue: paramValue
        },
        success: function (resp) {
            if (resp.state === 0) {
                userStateList = resp.data;
                loadingParamSelect($('#userState'), userStateList);
            }
        }
    });
}

/**
 * 查找一卡通状态
 * @param paramValue 0表示全部状态，2表示卡注销显示的状态
 */
function refreshCardStateList(paramValue) {
    $.ajax({
        url: 'param.do?method=findCardState',
        type: 'post',
        dataType: 'json',
        data: {
            paramValue: paramValue
        },
        success: function (resp) {
            if (resp.state === 0) {
                cardStateList = resp.data;
                loadingParamSelect($('#cardState'), cardStateList);
            }
        }
    });
}

/**
 * 查找一卡通申请状态
 */
function refreshApplyStateList() {
    $.ajax({
        url: 'param.do?method=findByType',
        type: 'post',
        dataType: 'json',
        data: {
            paramType: "一卡通申请状态"
        },
        success: function (resp) {
            if (resp.state === 0) {
                applyStateList = resp.data;
                loadingParamSelect($('#applyState'), applyStateList);
            }
        }
    });
}

/**
 * 查找所有收费员
 */
function refreshApplyUserList() {
    $.ajax({
        url: 'user.do?method=findByRoleId',
        type: 'post',
        dataType: 'json',
        data: {
            roleId: 2
        },
        success: function (resp) {
            if (resp.state === 0) {
                applyUserList = resp.data;
                loadingApplyUserSelect();
            }
        }
    });
}

/**
 * 查找所有医生
 */
function refreshDoctorList() {
    $.ajax({
        url: 'user.do?method=findByRoleId',
        type: 'post',
        dataType: 'json',
        data: {
            roleId: 1
        },
        success: function (resp) {
            if (resp.state === 0) {
                doctorList = resp.data;
                loadingDoctorSelect();
            }
        }
    });
}

/**
 * 查找所有工作人员
 */
function refreshWorkUserList() {
    $.ajax({
        url: 'user.do?method=findWorkUser',
        type: 'post',
        dataType: 'json',
        data: {},
        success: function (resp) {
            if (resp.state === 0) {
                workUserList = resp.data;
                loadingWorkUserSelect();
            }
        }
    });
}

/**
 * 查找参数类型
 */
function refreshParamTypeList() {
    $.ajax({
        url: 'param.do?method=findParamType',
        type: 'post',
        dataType: 'json',
        data: {},
        success: function (resp) {
            if (resp.state === 0) {
                paramTypeList = resp.data;
                loadingParamTypeSelect($('#paramType'));
                loadingParamTypeSelect($('#insert-paramType'));
                loadingParamTypeSelect($('#update-paramType'));
            }
        }
    });
}


/**
 * 将数据存入参数下拉框
 * @param $select jq下拉框对象
 * @param list 参数集合
 */
function loadingParamSelect($select, list) {
    for (let param of list) {
        let $option = $('<option></option>');
        $option.append(param.paramName);
        $option.attr('param', JSON.stringify(param));
        $select.append($option);
    }
}

/**
 * 将数据存入角色下拉框
 * @param $select jq下拉框对象
 */
function loadingRoleSelect($select) {
    for (let role of roleList) {
        let $option = $('<option></option>');
        $option.append(role.roleName);
        $option.attr('role', JSON.stringify(role));
        $select.append($option);
    }
}

/**
 * 将数据存入申请用户下拉框
 */
function loadingApplyUserSelect() {
    for (let user of applyUserList) {
        let $option = $('<option></option>');
        $option.append(user.name);
        $option.attr('user', JSON.stringify(user));
        $('#applyUser').append($option);
    }
}

/**
 * 将数据存入医生用户下拉框
 */
function loadingDoctorSelect() {
    for (let user of doctorList) {
        let $option = $('<option></option>');
        $option.append(user.name);
        $option.attr('user', JSON.stringify(user));
        $('#doctor').append($option);
    }
}

/**
 * 将数据存入工作人员用户下拉框
 */
function loadingWorkUserSelect() {
    for (let user of workUserList) {
        let $option = $('<option></option>');
        $option.append(user.name);
        $option.attr('user', JSON.stringify(user));
        $('#user').append($option);
    }
}

/**
 * 将数据存入参数类型下拉框
 */
function loadingParamTypeSelect($select) {
    for (let paramType of paramTypeList) {
        let $option = $('<option></option>');
        $option.append(paramType);
        $select.append($option);
    }
}