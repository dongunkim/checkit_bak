<%@ page contentType="text/html; charset=UTF-8"%>

<main class="content">
    <section class="req-page-wrap">
        <div class="rtit">유형별 신청 현황</div>

        <div class="board-search-wrap">
            <div class="form-item-wrap">
                <div class="label">진단신청명</div>
                <input type="text" class="inp w01" />
                <div class="label">상품명</div>
                <input type="text" class="inp w01" />
                <div class="label">진단유형</div>
                <select class="select">
                    <option>인프라</option>
                </select>
            </div>
            <div class="form-item-wrap">
                <div class="label">진행상태</div>
                <select class="select">
                    <option>진단신청 완료</option>
                </select>
                <div class="label">신청자</div>
                <input type="text" class="inp w01" />
                <div class="label">신청일자</div>
                <input type="text" id="sdate" class="inp datepicker" />
                <label for="sdate"><img src="../../assets/img/ico/ico-calendar01.svg" /></label>
                <span class="e01">~</span>
                <input type="text" id="edate" class="inp datepicker" />
                <label for="edate"><img src="/resoures/img/ico/ico-calendar01.svg" /></label>
                <button type="button" class="btn-func">조회</button>
            </div>
        </div>


        <div class="req-tbl-wrap">
            <div class="mtit">Total : 999</div>

            <!--grid -->
            <div class="tbl-item-wrap">
                <table id="list"><tr><td></td></tr></table>
                <div id="pager"></div>
                <div id="paginate"></div>
                <div class="nodata">조회결과가 없습니다.</div>

            </div>

        </div>
    </section>
</main>
