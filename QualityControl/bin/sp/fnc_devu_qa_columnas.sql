create or replace function fnc_devu_qa_columnas(
       pcod_empresa in char,
       pcod_sucursal in char,
       pcod_planilla in number) return varchar2 is

  vnveces number;
  vdivision number;
  --
  vcolumnas varchar2(3000) := '';
  vcount  number := 1;
  vsql    varchar2(5000);
begin
  begin
    select plc_nveces, plc_division
      into vnveces, vdivision
      from qa_planilla_config x
     where x.cod_empresa = pcod_empresa
       and x.cod_sucursal = pcod_sucursal
       and x.cod_planilla = pcod_planilla;
  exception
    when no_data_found then
      vnveces := 1;
      vdivision := 1;
  end;
  --si planilla es irregular entonces
  if vdivision = 1 then
    while vcount <= vnveces loop
      dbms_output.put_line(vcount);
      if vcount = 1 then
        vcolumnas := vcolumnas||'null "'||vcount||'"';
      else
        vcolumnas := vcolumnas||', null "'||vcount||'"';
      end if;
      vcount := vcount + 1;
    end loop;
  else --division = 0 ====> regular
    /* FALTA PROGRAMAR SEGUN CONFIGURACION DE PLANILLA */
    vcolumnas := ' NULL regular ';
  end if;
    dbms_output.put_line(vcount);
  if vnveces > 1 then
    vcolumnas := vcolumnas||', null Resultado';
  end if;

  vsql := 'select v.cod_empresa, '||
          '       v.cod_sucursal, '||
          '       v.cod_planilla, '||
          '       v.cod_variable, '||
          '       v.var_formula, '||
          '       decode(v.var_ori_datos, 0, '||chr(39)||'N'||chr(39)||', '||chr(39)||'S'||chr(39)||') es_formula, '||
          '       v.var_ordem orden, '||
          '       v.var_nom_var variable, '||
          '       v.var_nom_anali análisis, '||
          '       m.abreviatura medida, ';


  vsql:= vsql||vcolumnas;

  vsql := vsql||'  from qa_planilla        p, '||
                '         qa_variable        v, '||
                '         qa_unidad_medida   m, '||
                '         qa_planilla_config x '||
                '   where p.cod_empresa = v.cod_empresa '||
                '     and p.cod_sucursal = v.cod_sucursal '||
                '     and p.cod_planilla = v.cod_planilla '||
                '     and x.cod_empresa(+) = p.cod_empresa '||
                '     and x.cod_sucursal(+) = p.cod_sucursal '||
                '     and x.cod_planilla(+) = p.cod_planilla '||
                '     and m.cod_empresa = v.cod_empresa '||
                '     and m.cod_unidad = v.cod_unidad '||
                '     and p.estado = '||chr(39)||'A'||chr(39)||
                '     and p.cod_empresa = '||chr(39)||pcod_empresa||chr(39)||
                '     and p.cod_sucursal = '||chr(39)||pcod_sucursal||chr(39)||
                '     and p.cod_planilla = '||pcod_planilla||
                '   order by var_ordem';
  return vsql;
end;
