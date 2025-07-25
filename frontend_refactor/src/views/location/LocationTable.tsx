import { LocationResponseDto } from "@/dtos/location/location.dto";
import { useEmployeeStore } from "@/stores/useEmployeeStore";

interface Props {
  data: LocationResponseDto[];
  onView: (id: number) => void;
  onEdit: (id: number) => void;
  onDelete: (id: number) => void;
}

export function LocationTable({ data, onView, onEdit, onDelete }: Props) {
  const employee = useEmployeeStore((state) => state.employee);

  return (
    <table border={1} cellPadding={6} cellSpacing={0} style={{ width: "100%" }}>
      <thead>
        <tr>
          <th>#</th>
          <th>제목</th>
          <th>층</th>
          <th>홀</th>
          <th>섹션</th>
          <th>진열 형태</th>
          <th>비고</th>
          <th>액션</th>
        </tr>
      </thead>
      <tbody>
        {data.map((row, idx) => (
          <tr key={row.locationId}>
            <td>{idx + 1}</td>
            <td>{row.bookTitle}</td>
            <td>{row.floor}</td>
            <td>{row.hall}</td>
            <td>{row.section}</td>
            <td>{row.displayType === "BOOK_SHELF" ? "서가" : "평대"}</td>
            <td>{row.note}</td>
            <td>
              <button
                onClick={() => {
                 
                    onEdit(row.locationId);
                  
                }}
              >
                수정
              </button>{" "}
              <button
                onClick={() => {
                  if (
                    employee?.authorityName == "MANAGER" ||
                    employee?.authorityName == "ADMIN"
                  ) {
                    onDelete(row.locationId);
                  } else {
                    alert("권한이 없습니다.");
                  }
                }}
              >
                삭제
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
