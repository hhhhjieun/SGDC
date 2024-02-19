import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/MainView.vue'
import CompetitionView from '@/views/CompetitionView.vue'
import SoloView from '@/views/SoloView.vue'
import FeedView from '@/views/FeedView.vue'
import FeedDetailView from '@/views/FeedDetailView.vue'
import MyPageView from '@/views/MyPageView.vue'
import MyPageUpdateView from '@/views/MyPageUpdateView.vue'
import SsallowView from '@/views/SsallowView.vue'
import LoginView from '@/views/LoginView.vue'
import SignUpView from '@/views/SignUpView.vue'
import SearchNickname from '@/components/Common/SearchNickname.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Main',
      component: MainView,
    },

    {
      path: '/competition',
      name: 'Competiton',
      component: CompetitionView,
    },
    {
      path: '/solo',
      name: 'Solo',
      component: SoloView,
    },
    {
      path: '/feed',
      name: 'Feed',
      component: FeedView,
    },
    {
      path: '/feeddetail',
      name: 'FeedDetail',
      component: FeedDetailView,
      props: true,
    },
    {
      path: '/mypage/:userId',
      name: 'MyPage',
      component: MyPageView,
      props: true,
    },
    {
      path: '/profile/:userId',
      name: 'Profile',
      component: MyPageView,
      props: true,
    },
    {
      path: '/mypageupdate',
      name: 'MyPageUpdate',
      component: MyPageUpdateView,
    },
    {
      path: '/ssallow/:userId',
      name: 'Ssallow',
      component: SsallowView,
    },
    {
      path: '/login',
      name: 'Login',
      component: LoginView,
    },
    {
      path: '/signup',
      name: 'SignUp',
      component: SignUpView,
    },
    
  ]
})

export default router
