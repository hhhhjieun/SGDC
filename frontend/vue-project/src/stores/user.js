
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';



export const useUserStore = defineStore('user', () => {
    const URL = 'http://localhost:8080/user';
    
    // 마이페이지 사용자 정보
    const userData = function (userId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/user-info/${userId.value}`)
                .then((res) => {
                    console.log(res);
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }

    // 마이페이지 회원 수정
    const userUpdate = function (userId, updateData) {
        return new Promise((resolve, reject) => {
            axios
                .patch(`${URL}/user-info/${userId.value}`, updateData)
                .then((res) => {
                    console.log(res);
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }

    // 대표 뱃지
    const mainBadge = function (userId, badgeId) {
        return new Promise((resolve, reject) => {
            axios
                .patch(`${URL}/${userId.value}/${badgeId.value}`, {})
                .then((res) => {
                    console.log(res);
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
            axios
                .get(`${URL}/search`,user_nickname)
                .then((res) => {
                    console.log(res);
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
            axios
                .get(`${URL}/friends/${userId.value}`, user_nickname)
                .then((res) => {
                    console.log(res);
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
        mainBadge, 
        findAllfriends, 
        findMyfriends,
    }
})


