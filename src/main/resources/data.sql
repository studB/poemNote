-- pn_user setting
insert into pn_user values( 1, 'EDITOR', 0, '@bdy4321', true, true );
insert into pn_user values( 2, 'READER', 10, '4321', false, true );
insert into pn_user values( 3, 'VIEWER' , 100, '', false, false );
-- pn_category_meta setting
insert into pn_category_meta values( 1, 'POEM', 11, true, true, true, true, true, true, true );
insert into pn_category_meta values( 2, 'LINE', 111, false, false, false, false, false, false, false );
insert into pn_category_meta values( 3, 'OPINION', 111, false, true, false, true, false, false, false );
insert into pn_category_meta values( 4, 'THEORY', 111, false, true, true, true, false, false, false );
insert into pn_category_meta values( 5, 'REVIEW', 111, true, true, true, true, false, true, true );