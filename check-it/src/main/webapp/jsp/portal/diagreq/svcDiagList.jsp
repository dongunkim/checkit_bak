<%@ page contentType="text/html; charset=UTF-8"%>
        <main class="content">
            <section class="req-page-wrap">
                <div class="rtit">상품별 신청 현황</div>

                <div class="board-search-wrap">
                    <div class="form-item-wrap">
                        <div class="label">상품명</div>
                        <input type="text" class="inp" />
                        <button type="button" class="btn-func">조회</button>
                    </div>
                </div>

                <div class="board-btn-wrap">
                    <button type="button">진단 신청</button>
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
