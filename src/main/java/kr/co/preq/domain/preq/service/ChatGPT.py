# -*- coding: utf-8 -*-
import openai

openai.api_key = "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" # API Key
completion = openai.ChatCompletion.create(
    model="gpt-3.5-turbo",
    messages=[
        {
            "role": "system",
            "content": "너는 회사 면접관이고, 지원자의 지원서를 보고 면접 질문을 하는거야"
        },
        {
            "role": "user",
            "content": "제 강점은 책임감과 열정이라고 생각합니다. 제가 맡은 부분을 다 하는 것은 물론이고, 다른 분이 도움을 요청하면 제가 할 수 있는 선에서 최대한 도와드리려고 노력합니다. 또한, 제 마음에 들 때까지 프로젝트의 완성도를 높이기 위해 열을 다하는 편입니다. 2022 Unid-DTHON에 참가하여 무박 2일동안 MZ세대를 위한 서비스를 주제로 프로젝트를 진행한 적이 있습니다. 백엔드는 총 3명이었고, 기능 명세를 같이 작성한 후 각자 5개씩 API를 맡게 되었습니다. 저를 포함한 백엔드 분들이 엄청 능숙한 편은 아니었기에, 개발에 시간이 좀 걸린 상황이었습니다. 그런데 마감이 얼마 안 남았을 즈음, 다른 분이 작업하신 API에 오류가 다수 있다는 점을 발견하였습니다. 한 분은 거의 포기하셔서 주무시고, 한 분은 다른 API를 개발하시느라 시간이 없으셔서, 제가 그 오류들을 수정하기 시작했습니다. 결국 오류를 모두 고치지는 못했지만, 프로젝트를 완성해야 한다는 책임감과 오류를 고치겠다는 열정으로 마지막 1분 전까지 한숨도 자지 않고 개발에 몰두했습니다. 그 결과 안타깝게 수상은 하지 못했지만, 심사위원 분들께 심도 있는 질문을 받으며 발표를 성공적으로 마무리할 수 있었습니다. "
        }
    ],
)

print(completion)