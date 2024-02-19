
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { authorizationAPI } from './authAPI';
import { serverURL, v1_URL } from './config';


export const useUserStore = defineStore('user', () => {
    const URL = serverURL + v1_URL + 'user';

    // 마이페이지 사용자 정보
    const userData = function (userId) {

        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/user-info/${userId}`)
                .then((res) => {
                    // console.log('남은 도전장 개수 확인해봅시다.')
                    // console.log(res);
                    resolve(res);
                })
                .catch((err) => {
                    // console.log('남은 도전장 개수 확인해봅시다. 에러')
                    console.log(err);
                    reject(err);
                });
        });
    }

    // 마이페이지 회원 수정
    const userUpdate = function (userId, updateData) {
        // console.log(updateData)
        return new Promise((resolve, reject) => {
            authorizationAPI
                .patch(`${URL}/user-info-modify/${userId}`, updateData)
                .then((res) => {
                    // console.log(res);
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }

    const userProfileUpdate = function (userId, formData) {
        // console.log('axios');
        // for (let [key, value] of formData.entries()) {
        //     console.log(`${key}: ${value}`);
        // }
        return new Promise((resolve, reject) => {
            authorizationAPI.request({
                method: 'patch',
                url: `${URL}/user-profile-modify/${userId}`,
                data: formData,
                headers: { 'Content-Type': 'multipart/form-data' }
            })
                .then(res => {
                    resolve(res);
                    // console.log('업로드 완료');
                })
                .catch(err => {
                    console.log(err);
                    reject(err);
                });
        });
    }

    // 대표 뱃지
    const mainBadge = function (userId, badgeId) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .patch(`${URL}/${userId}/${badgeId}`, {})
                .then((res) => {
                    // console.log(res);
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err)
                });
        });
    }

    // 닉네임 검색(전체)
    const findAllfriends = function (user_nickname) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/search-nickname`, {
                    params: {
                        keyword: user_nickname
                    }
                })
                .then((res) => {
                    // console.log(res);
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }

    // 닉네임 검색(친구)
    const findMyfriends = function (userId, user_nickname) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/friends/${userId.value}`, { params: { user_nickname } })
                .then((res) => {
                    // console.log(res);
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }

    return {
        userData,
        userUpdate,
        userProfileUpdate,
        mainBadge,
        findAllfriends,
        findMyfriends,
    }
})



