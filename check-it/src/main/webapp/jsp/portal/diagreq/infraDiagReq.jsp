<%@ page contentType="text/html; charset=UTF-8"%>
        <main class="content">

            <section class="req-page-wrap">
                <div class="rtit">인프라 진단 신청</div>

                <div class="req-guide-wrap">
                    <div class="mtit">▶ INFO. 진단신청 가이드</div>
                    <ul>
                        <li>
                            <div class="gheader">STEP 1. 진단신청 기본정보</div>
                            <div class="gcont">
                                <ul>
                                    <li>상품서비스명</li>
                                    <li>진단신청명</li>
                                    <li>오픈예정일</li>
                                </ul>
                            </div>
                        </li>
                        <li>
                            <div class="gheader">STEP 2. 진단대상 서버 등록</div>
                            <div class="gcont">
                                <ul>
                                    <li>서버 추가</li>
                                    <li>서버분류 등록</li>
                                </ul>
                            </div>
                        </li>
                        <li>
                            <div class="gheader">STEP 3. 서버 세부정보 등록</div>
                            <div class="gcont">
                                <ul>
                                    <li>진단대상별 세부 정보 등록</li>
                                    <li>계정정보 등록</li>
                                    <li>조치담당자 등록</li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>

                <div class="req-form-wrap">
                    <div class="mtit">▶ STEP 1. 진단 기본 정보 입력하기</div>
                    <ul>
                        <li>
                            <div class="cols-item-wrap">
                                <div class="label">진단신청명*</div>
                                <div class="rows-item-wrap">
                                    <div class="form-item-wrap">
                                        <input type="text" class="inp" placeholder="진단신청명" />
                                        <div class="search-item-wrap">
                                            <input type="text" class="inp" placeholder="기존 신청 이력" />
                                            <button type="button"><img src="/resources/portal/img/ico/ico-search-blue.svg" /></button>
                                        </div>
                                    </div>
                                    <div class="form-item-wrap">
                                        <label>
                                            <input type="checkbox" />
                                            <span>임시저장 목록 불러오기</span>
                                        </label>
                                    </div>
                                    <div class="form-item-wrap">
                                        <select class="select">
                                            <option>선택</option>
                                            <option>IT 보안 진단 시스템 고도화 – 김신청 (2022-06-01 14:20)</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="cols-item-wrap">
                                <div class="label">상품서비스명*</div>
                                <div class="rows-item-wrap">
                                    <div class="form-item-wrap">
                                        <div class="search-item-wrap t01">
                                            <input type="text" class="inp" placeholder="통합 IT 진단 시스템 (김담당)" />
                                            <button type="button"><img src="/resources/portal/img/ico/ico-search-blue.svg" /></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="cols-item-wrap">
                                <div class="label">오픈 예정일*</div>
                                <div class="rows-item-wrap">
                                    <div class="form-item-wrap">
                                        <input type="text"id="sdate" class="inp datepicker" />
                                        <label for="sdate">
                                            <img src="/resources/portal/img/ico/ico-calendar01.svg" />
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>

                <div class="req-tbl-wrap">
                    <div class="mtit">▶ STEP 1. 진단 기본 정보 입력하기</div>
                    <div class="req-btn-wrap">
                        <button type="button" class="btn-add">서버 추가</button>
                    </div>
                    <!--grid -->
                    <div class="tbl-item-wrap">
                        <table id="list"><tr><td></td></tr></table>
                        <!--<div id="pager"></div>-->
                    </div>

                    <div class="req-btn-wrap">
                        <button type="button" class="btn-remove">선택된 서버 삭제</button>
                    </div>
                </div>
            </section>


        </main>
