package com.smartdengg.presentation.user;

import architecture.domain.entity.UserDetailEntity;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 创建时间:  2017/08/10 11:15 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
class UserConverter {

  private UserConverter() {
    throw new AssertionError("no instance");
  }

  static Flowable<List<UserDetailModel>> convertDetail(List<UserDetailEntity> userDetailEntities) {
    Objects.requireNonNull(userDetailEntities, "userDetailEntities == null");

    List<UserDetailModel> userDetailModels = new ArrayList<>(userDetailEntities.size());
    for (UserDetailEntity detailResponse : userDetailEntities) {
      UserDetailModel userDetailModel = new UserDetailModel();
      userDetailModel.name = detailResponse.name;
      userDetailModel.address = detailResponse.address;
      userDetailModels.add(userDetailModel);
    }
    return Flowable.just(userDetailModels);
  }
}
