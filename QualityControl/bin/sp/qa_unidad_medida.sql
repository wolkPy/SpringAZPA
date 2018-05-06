-- Create table
create table QA_UNIDAD_MEDIDA
(
  cod_empresa VARCHAR2(5) default '01' not null,
  cod_unidad  NUMBER(3) not null,
  descipcion  VARCHAR2(30) not null,
  abreviatura VARCHAR2(20) not null
)
tablespace BS_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 40K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table QA_UNIDAD_MEDIDA
  add primary key (COD_EMPRESA, COD_UNIDAD)
  using index 
  tablespace BS_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 80K
    next 1M
    minextents 1
    maxextents unlimited
  );
